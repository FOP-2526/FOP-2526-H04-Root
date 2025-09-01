package h04.participants;

import fopbot.Direction;
import fopbot.World;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.ConstructorLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.lang.reflect.Modifier;
import java.util.function.Supplier;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

public abstract class AbstractParticipantTests {

    protected void testDefinition(Supplier<TypeLink> typeLinkSupplier, String className) {
        TypeLink typeLink = assertCallNotNull(typeLinkSupplier::get, emptyContext(),
            r -> "Could not find class h04.participants." + className);
        assertEquals(Participant.class, typeLink.superType().reflection(), emptyContext(),
            r -> "Class h04.participants.%s does not extend h04.participants.Participant".formatted(className));
        assertFalse((typeLink.modifiers() & Modifier.ABSTRACT) != 0, emptyContext(),
            r -> "Class h04.participants.%s should not be abstract".formatted(className));
    }

    protected Participant testConstructor(Supplier<ConstructorLink> constructorLinkSupplier, String className, Species species, Direction direction) {
        ConstructorLink constructorLink = assertCallNotNull(constructorLinkSupplier::get, emptyContext(),
            r -> "Class h04.participants.%s does not have a constructor %s(int, int)".formatted(className, className));

        World.setSize(1, 1);
        World.setDelay(0);
        World.setVisible(false);

        Context context = contextBuilder()
            .add("x", 0)
            .add("y", 0)
            .build();
        Participant participant = callObject(() -> constructorLink.invoke(0, 0), context,
            r -> "An exception occurred while invoking constructor %s(int, int)".formatted(className));

        assertEquals(0, participant.getX(), context,
            r -> "%s(int, int) did not invoke the super constructor with the correct X coordinate".formatted(className));
        assertEquals(0, participant.getY(), context,
            r -> "%s(int, int) did not invoke the super constructor with the correct Y coordinate".formatted(className));
        assertEquals(species, participant.getSpecies(), context,
            r -> "%s(int, int) did not invoke the super constructor with the correct species".formatted(className));
        assertEquals(direction, participant.getOrientation(), context,
            r -> "%s(int, int) did not invoke the super constructor with the correct direction".formatted(className));

        return participant;
    }
}
