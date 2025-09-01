package h04.participants;

import fopbot.Direction;
import fopbot.Transition;
import fopbot.World;
import h04.Links;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.FieldLink;

import java.awt.*;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class RockTests extends AbstractParticipantTests {

    @Test
    public void testDefinition() {
        super.testDefinition(Links.ROCK_TYPE, "Rock");

        FieldLink roundsWonFieldLink = assertCallNotNull(Links.ROCK_ROUNDS_WON_FIELD_LINK::get, emptyContext(),
            r -> "Could not find field roundsWon in class h04.participants.Rock");
        assertEquals(int.class, roundsWonFieldLink.type().reflection(), emptyContext(),
            r -> "Field roundsWon in class h04.participants.Rock is not of type int");
        assertFalse((roundsWonFieldLink.modifiers() & Modifier.STATIC) != 0, emptyContext(),
            r -> "Field roundsWon in class h04.participants.Rock is static");
        assertFalse((roundsWonFieldLink.modifiers() & Modifier.FINAL) != 0, emptyContext(),
            r -> "Field roundsWon in class h04.participants.Rock is final");
    }

    @Test
    public void testConstructor() {
        Participant rockInstance = super.testConstructor(Links.ROCK_CONSTRUCTOR_LINK, "Rock", Species.ROCK, Direction.UP);

        assertCallEquals(0, () -> Links.ROCK_ROUNDS_WON_FIELD_LINK.get().get(rockInstance), emptyContext(),
            r -> "Field roundsWon in class h04.participants.Rock does not have the correct value");
        assertEquals(3, rockInstance.getNumberOfCoins(), emptyContext(),
            r -> "Rock-Robot does not have the correct amount of coins after initializing");
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5})
    public void testDoVictoryDance_FittingWorld(int roundsWon) {
        testDoVictoryDance(8, 8, roundsWon);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5})
    public void testDoVictoryDance_NarrowWorld(int roundsWon) {
        testDoVictoryDance(2, 8, roundsWon);
    }

    private void testDoVictoryDance(int worldWidth, int worldHeight, int roundsWon) {
        World.setSize(worldWidth, worldHeight);
        World.setDelay(0);
        World.setVisible(false);

        Context context = contextBuilder()
            .add("world dimensions (w x h)", "%d x %d".formatted(worldWidth, worldHeight))
            .add("initial position", "(0, 0)")
            .add("roundsWon", roundsWon)
            .build();
        Participant rockInstance = callObject(() -> Links.ROCK_CONSTRUCTOR_LINK.get().invoke(0, 0), context,
            r -> "An exception occurred while invoking Rock(int, int)");
        Links.ROCK_ROUNDS_WON_FIELD_LINK.get().set(rockInstance, roundsWon);

        call(rockInstance::doVictoryDance, context,
            r -> "An exception occurred while invoking Rock.doVictoryDance()");

        BiPredicate<Integer, Integer> posInWorld = (x, y) -> x < worldWidth && x >= 0 && y < worldHeight && y >= 0;
        List<Transition.RobotAction> expectedActions = new ArrayList<>();
        Direction simulatedDir = Direction.UP;
        for (int i = 0, simulatedX = 0, simulatedY = 0; i < 4; i++) {
            for (int j = 0; j < roundsWon && posInWorld.test(simulatedX + simulatedDir.getDx(), simulatedY + simulatedDir.getDy()); j++) {
                expectedActions.add(Transition.RobotAction.MOVE);
                simulatedX += simulatedDir.getDx();
                simulatedY += simulatedDir.getDy();
            }
            expectedActions.add(Transition.RobotAction.TURN_LEFT);
            expectedActions.add(Transition.RobotAction.TURN_LEFT);
            expectedActions.add(Transition.RobotAction.TURN_LEFT);
            simulatedDir = Direction.values()[(simulatedDir.ordinal() + 1) % 4];
        }
        List<Transition.RobotAction> actualActions = World.getGlobalWorld()
            .getTrace(rockInstance)
            .getTransitions()
            .stream()
            .filter(transition -> transition.action == Transition.RobotAction.TURN_LEFT || transition.action == Transition.RobotAction.MOVE)
            .sorted(Comparator.comparingInt(transition -> transition.step))
            .map(transition -> transition.action)
            .toList();

        assertEquals(expectedActions, actualActions, context,
            r -> "Rock.doVictoryDance() did not perform the expected actions");
    }

    public enum Outcome {
        WIN(Species.SCISSORS), DRAW(Species.ROCK), LOSS(Species.PAPER);

        private final Species opponentSpecies;

        Outcome(Species opponentSpecies) {
            this.opponentSpecies = opponentSpecies;
        }
    }

    @ParameterizedTest
    @EnumSource(value = Outcome.class, names = {"WIN"})
    public void testFightWin(Outcome outcome) {
        testFight(outcome, outcome.opponentSpecies);
    }

    @ParameterizedTest
    @EnumSource(value = Outcome.class, names = {"DRAW", "LOSS"})
    public void testFightOther(Outcome outcome) {
        testFight(outcome, outcome.opponentSpecies);
    }

    private void testFight(Outcome expectedOutcome, Species opponentSpecies) {
        World.setSize(2, 2);
        World.setDelay(0);
        World.setVisible(false);

        Context context = contextBuilder()
            .add("world dimensions (w x h)", "%d x %d".formatted(2, 2))
            .add("Rock initial position", "(0, 0)")
            .add("opponent initial position", "(1, 0)")
            .add("opponent species", opponentSpecies)
            .build();
        Participant rockInstance = callObject(() -> Links.ROCK_CONSTRUCTOR_LINK.get().invoke(0, 0), context,
            r -> "An exception occurred while invoking Rock(int, int)");

        Participant participantMock = Mockito.mock(Participant.class, Mockito.withSettings()
            .useConstructor(opponentSpecies, 1, 0, Direction.UP)
            .defaultAnswer(Mockito.CALLS_REAL_METHODS));
        Mockito.when(participantMock.fight(Mockito.any())).thenReturn(null);

        Participant victor = callObject(() -> rockInstance.fight(participantMock), context,
            r -> "An exception occurred while invoking Rock.fight(Participant)");
        if (expectedOutcome == Outcome.WIN) {
            assertSame(rockInstance, victor, context,
                    r -> "Rock.fight(Participant) did not return the correct value (itself / 'this')");
            assertTrue(participantMock.isTurnedOff(), context,
                    r -> "Rock.fight(Participant) did not turn off the losing opponent");
            assertEquals(1, Links.ROCK_ROUNDS_WON_FIELD_LINK.get().get(rockInstance), context,
                r -> "Rock.fight(Participant) did not increment its roundsWon field");
        } else {
            assertSame(participantMock, victor, context,
                r -> "Rock.fight(Participant) did not return the correct value (opponent)");
            assertTrue(rockInstance.isTurnedOff(), context,
                r -> "Rock.fight(Participant) did not turn itself off despite losing");
            assertEquals(0, Links.ROCK_ROUNDS_WON_FIELD_LINK.get().get(rockInstance), context,
                r -> "Rock.fight(Participant) was not supposed to increment its roundsWon field");
        }
    }
}
