package h04;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import kotlin.Pair;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.match.MatchingUtils;
import org.tudalgo.algoutils.tutor.general.reflections.*;
import spoon.SpoonException;
import spoon.reflect.declaration.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;
import static org.sourcegrade.jagr.api.testing.extension.TestCycleResolver.getTestCycle;

@TestForSubmission
@SuppressWarnings({"UnstableApiUsage", "NewClassNamingConvention", "unchecked"})
public class JavadocExtractor<T extends Link & WithCtElement> {

    private static final Set<String> IGNORE_LIST = Set.of(
        "h04.H04_RubricProviderJavadoc",
        "h04.JavadocExtractor",
        "h04.participants.AbstractParticipantTests",
        "h04.participants.PaperTests",
        "h04.participants.RockTests",
        "h04.participants.ScissorsTests",
        "h04.AccessTransformer",
        "h04.H04_RubricProviderPrivate",
        "h04.Links",
        "h04.RefereeTests",
        "h04.TestUtils",
        "h04.participants.Participant",
        "h04.participants.Species",
        "h04.Main",
        "h04.Referee",
        "h04.Utils",
        "h04.ExampleJUnitTest"
    );
    private static final List<BasicTypeLink> CLASSES = BasicPackageLink.of("h04", true).getTypes()
        .stream()
        .filter(typeLink -> !IGNORE_LIST.contains(typeLink.reflection().getName()))
        .toList();
    private static final String TU_ID = getTestCycle() != null ? getTestCycle().getSubmission().getInfo().split("_")[1] : "";
    private static final List<String> SOLUTION_MEMBERS;

    static {
        try {
            SOLUTION_MEMBERS = new ArrayList<>();
            ((List<Map<String, ?>>) new ObjectMapper().readValue(JavadocExtractor.class.getResourceAsStream("/structure.json"), List.class))
                .forEach(object -> {
                    @SuppressWarnings("rawtypes")
                    Class<? extends CtType> ctTypeClass = switch ((String) object.get("type")) {
                        case "class" -> CtClass.class;
                        case "interface" -> CtInterface.class;
                        case "enum" -> CtEnum.class;
                        default -> null;
                    };
                    SOLUTION_MEMBERS.add(toName(ctTypeClass, (String) object.get("typeName")));

                    for (Map<String, String> constructor : (List<Map<String, String>>) object.get("declaredConstructors")) {
                        SOLUTION_MEMBERS.add(toName(CtConstructor.class, constructor.get("name"), constructor.get("parameters")));
                    }
                    for (Map<String, String> methods : (List<Map<String, String>>) object.get("declaredMethods")) {
                        SOLUTION_MEMBERS.add(toName(CtMethod.class, methods.get("name"), methods.get("parameters"), (String) object.get("typeName")));
                    }
                });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void extractJavadoc() {
        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode().addAll(((List<T>) CLASSES).stream()
            .flatMap(this::mapToMembers)
            .filter(this::mustBeDocumented)
            .map(this::toObjectNode)
            .toList());
        fail(arrayNode.toPrettyString());
    }

    private Stream<T> mapToMembers(T link) {
        TypeLink typeLink = (TypeLink) link;
        return Stream.concat(
            Stream.of(link),
            Stream.concat(
                (Stream<? extends T>) typeLink.getConstructors().stream(),
                (Stream<? extends T>) typeLink.getMethods().stream()
            )
        );
    }

    private boolean mustBeDocumented(T link) {
        return switch (link) {
            case BasicTypeLink typeLink -> !Modifier.isPrivate(typeLink.modifiers());
            case BasicConstructorLink constructorLink -> {
                // filter out default constructors
                try {
                    constructorLink.getCtElement().getOriginalSourceFragment();
                } catch (SpoonException e) {
                    yield false;
                } catch (Exception ignored) {}
                yield !Modifier.isPrivate(constructorLink.modifiers());
            }
            case BasicMethodLink methodLink -> {
                Method method = methodLink.reflection();
                Class<?> declaringClass = method.getDeclaringClass();
                if (declaringClass.isEnum()) {
                    // filter out semi-synthetic enum methods values() and valueOf(String)
                    try {
                        link.getCtElement();
                    } catch (Exception e) {
                        yield false;
                    }
                }
                yield !Modifier.isPrivate(methodLink.modifiers());
            }
            case null, default -> false;
        };
    }

    private ObjectNode toObjectNode(T link) {
        String actualName = toName(link.getCtElement());
        String expectedName = SOLUTION_MEMBERS.stream()
            .map(s -> new Pair<>(s, MatchingUtils.similarity(s, actualName)))
            .filter(pair -> pair.getSecond() >= 0.80)
            .max(Comparator.comparing(Pair::getSecond))
            .map(Pair::getFirst)
            .orElse("?");
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put("tu_id", TU_ID);
        objectNode.put("expectedName", expectedName);
        objectNode.put("actualName", actualName);
        objectNode.put("documentation", link.getCtElement().getDocComment().replaceAll("(\n)+", "\n").trim());
        return objectNode;
    }

    private String toName(CtElement ctElement) {
        return switch (ctElement) {
            case CtEnum<?> e -> toName(CtEnum.class, e.getQualifiedName());
            case CtClass<?> c -> toName(CtClass.class, c.getQualifiedName());
            case CtInterface<?> i -> toName(CtInterface.class, i.getQualifiedName());
            case CtConstructor<?> c -> toName(CtConstructor.class, ((CtType<?>) c.getParent()).getQualifiedName(), toTypes(c.getParameters()));
            case CtMethod<?> m -> toName(CtMethod.class, m.getSimpleName(), toTypes(m.getParameters()), ((CtType<?>) m.getParent()).getQualifiedName());
            default -> throw new IllegalArgumentException("Unsupported type: " + ctElement.getClass());
        };
    }

    private static String toName(Class<? extends CtNamedElement> ctClass, String... values) {
        if (ctClass == CtEnum.class) {
            return "enum %s".formatted((Object[]) values);
        } else if (ctClass == CtClass.class) {
            return "class %s".formatted((Object[]) values);
        } else if (ctClass == CtInterface.class) {
            return "interface %s".formatted((Object[]) values);
        } else if (ctClass == CtConstructor.class) {
            return "constructor %s(%s)".formatted((Object[]) values);
        } else if (ctClass == CtMethod.class) {
            return "method %s(%s) in type %s".formatted((Object[]) values);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + ctClass);
        }
    }

    private String toTypes(List<CtParameter<?>> parameters) {
        return parameters.stream()
            .map(parameter -> parameter.getType().getQualifiedName())
            .collect(Collectors.joining(", "));
    }
}
