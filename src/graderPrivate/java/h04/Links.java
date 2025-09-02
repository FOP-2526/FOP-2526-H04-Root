package h04;

import com.google.common.base.Suppliers;
import h04.participants.Participant;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.reflections.*;

import java.util.List;
import java.util.function.Supplier;

public class Links {

    private static final Matcher<ConstructorLink> CONSTRUCTOR_MATCHER = Matcher.of(constructorLink -> {
        List<? extends TypeLink> parameterTypes = constructorLink.typeList();
        return parameterTypes.size() == 2
            && parameterTypes.get(0).equals(BasicTypeLink.of(int.class))
            && parameterTypes.get(1).equals(BasicTypeLink.of(int.class));
    });

    public static final PackageLink BASE_PACKAGE_LINK = BasicPackageLink.of("h04");
    public static final PackageLink PARTICIPANTS_PACKAGE_LINK = BasicPackageLink.of("h04.participants");

    public static final Supplier<TypeLink> PAPER_TYPE = Suppliers.memoize(() ->
        PARTICIPANTS_PACKAGE_LINK.getType(Matcher.of(typeLink -> typeLink.name().equals("Paper"))));
    public static final Supplier<ConstructorLink> PAPER_CONSTRUCTOR_LINK = Suppliers.memoize(() ->
        PAPER_TYPE.get().getConstructor(CONSTRUCTOR_MATCHER));
    public static final Supplier<FieldLink> PAPER_NUMBER_OF_PAPERS_FIELD_LINK = Suppliers.memoize(() ->
        PAPER_TYPE.get().getField(Matcher.of(fieldLink -> fieldLink.name().equals("NUMBER_OF_PAPERS"))));
    public static final Supplier<FieldLink> PAPER_STARTING_X_POSITION_FIELD_LINK = Suppliers.memoize(() ->
        PAPER_TYPE.get().getField(Matcher.of(fieldLink -> fieldLink.name().equals("startingXPosition"))));

    public static final Supplier<TypeLink> ROCK_TYPE = Suppliers.memoize(() ->
        PARTICIPANTS_PACKAGE_LINK.getType(Matcher.of(typeLink -> typeLink.name().equals("Rock"))));
    public static final Supplier<ConstructorLink> ROCK_CONSTRUCTOR_LINK = Suppliers.memoize(() ->
        ROCK_TYPE.get().getConstructor(CONSTRUCTOR_MATCHER));
    public static final Supplier<FieldLink> ROCK_ROUNDS_WON_FIELD_LINK = Suppliers.memoize(() ->
        ROCK_TYPE.get().getField(Matcher.of(fieldLink -> fieldLink.name().equals("roundsWon"))));

    public static final Supplier<TypeLink> SCISSORS_TYPE = Suppliers.memoize(() ->
        PARTICIPANTS_PACKAGE_LINK.getType(Matcher.of(typeLink -> typeLink.name().equals("Scissors"))));
    public static final Supplier<ConstructorLink> SCISSORS_CONSTRUCTOR_LINK = Suppliers.memoize(() ->
        SCISSORS_TYPE.get().getConstructor(CONSTRUCTOR_MATCHER));

    public static final Supplier<TypeLink> REFEREE_TYPE = Suppliers.memoize(() ->
        BASE_PACKAGE_LINK.getType(Matcher.of(typeLink -> typeLink.name().equals("Referee"))));
    public static final Supplier<FieldLink> REFEREE_PARTICIPANTS_FIELD_LINK = Suppliers.memoize(() ->
        REFEREE_TYPE.get().getField(Matcher.of(fieldLink -> fieldLink.name().equals("participants"))));
    public static final Supplier<FieldLink> REFEREE_LINE_UP_FIELD_LINK = Suppliers.memoize(() ->
        REFEREE_TYPE.get().getField(Matcher.of(fieldLink -> fieldLink.name().equals("lineUp"))));
    public static final Supplier<MethodLink> REFEREE_ASCEND_METHOD_LINK = Suppliers.memoize(() ->
        REFEREE_TYPE.get().getMethod(Matcher.of(methodLink -> methodLink.name().equals("ascend")
            && methodLink.typeList().equals(List.of(BasicTypeLink.of(Participant[].class))))));
    public static final Supplier<MethodLink> REFEREE_MOVE_UP_METHOD_LINK = Suppliers.memoize(() ->
        REFEREE_TYPE.get().getMethod(Matcher.of(methodLink -> methodLink.name().equals("moveUp")
            && methodLink.typeList().equals(List.of(BasicTypeLink.of(Participant.class))))));
    public static final Supplier<MethodLink> REFEREE_PLACE_LINE_UP_METHOD_LINK = Suppliers.memoize(() ->
        REFEREE_TYPE.get().getMethod(Matcher.of(methodLink -> methodLink.name().equals("placeLineUp")
            && methodLink.typeList().isEmpty())));
    public static final Supplier<MethodLink> REFEREE_GET_OPPONENT_METHOD_LINK = Suppliers.memoize(() ->
        REFEREE_TYPE.get().getMethod(Matcher.of(methodLink -> methodLink.name().equals("getOpponent")
            && methodLink.typeList().equals(List.of(BasicTypeLink.of(Participant.class))))));
    public static final Supplier<MethodLink> REFEREE_DETERMINE_VICTORS_METHOD_LINK = Suppliers.memoize(() ->
        REFEREE_TYPE.get().getMethod(Matcher.of(methodLink -> methodLink.name().equals("determineVictors")
            && methodLink.typeList().equals(List.of(BasicTypeLink.of(Participant[].class))))));
    public static final Supplier<MethodLink> REFEREE_DO_ROUND_METHOD_LINK = Suppliers.memoize(() ->
        REFEREE_TYPE.get().getMethod(Matcher.of(methodLink -> methodLink.name().equals("doRound")
            && methodLink.typeList().isEmpty())));
}
