package h04.participants;

import fopbot.Direction;
import fopbot.Transition;
import fopbot.World;
import h04.Links;
import h04.TestUtils;
import org.junit.jupiter.api.BeforeEach;
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

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class PaperTests extends AbstractParticipantTests {

    @BeforeEach
    public void setup() {
        try {
            TestUtils.setNumericField(Links.PAPER_NUMBER_OF_PAPERS_FIELD_LINK.get(), null, 0);
        } catch (Exception e) {
            System.err.println("Could not set field numberOfPapers in class h04.participants.Paper");
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    @Test
    public void testDefinition() {
        super.testDefinition(Links.PAPER_TYPE, "Paper");

        FieldLink numberOfPapersFieldLink = assertCallNotNull(Links.PAPER_NUMBER_OF_PAPERS_FIELD_LINK::get, emptyContext(),
            r -> "Could not find field numberOfPapers in class h04.participants.Paper");
        assertTrue(TestUtils.isNumericType(numberOfPapersFieldLink.reflection().getType()), emptyContext(),
            r -> "Field numberOfPapers in class h04.participants.Paper is not of a numeric type");
        assertTrue((numberOfPapersFieldLink.modifiers() & Modifier.STATIC) != 0, emptyContext(),
            r -> "Field numberOfPapers in class h04.participants.Paper is not static");
        assertFalse((numberOfPapersFieldLink.modifiers() & Modifier.FINAL) != 0, emptyContext(),
            r -> "Field numberOfPapers in class h04.participants.Paper is final");

        FieldLink startingXPositionFieldLink = assertCallNotNull(Links.PAPER_STARTING_X_POSITION_FIELD_LINK::get, emptyContext(),
            r -> "Could not find field startingXPosition in class h04.participants.Paper");
        assertTrue(TestUtils.isNumericType(startingXPositionFieldLink.reflection().getType()), emptyContext(),
            r -> "Field startingXPosition in class h04.participants.Paper is not of a numeric type");
        assertFalse((startingXPositionFieldLink.modifiers() & Modifier.STATIC) != 0, emptyContext(),
            r -> "Field startingXPosition in class h04.participants.Paper is static");
        assertTrue((startingXPositionFieldLink.modifiers() & Modifier.FINAL) != 0, emptyContext(),
            r -> "Field startingXPosition in class h04.participants.Paper is not final");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    public void testConstructor(int numberOfPapersValue) {
        TestUtils.setNumericField(Links.PAPER_NUMBER_OF_PAPERS_FIELD_LINK.get(), null, numberOfPapersValue);

        Participant paperInstance = super.testConstructor(Links.PAPER_CONSTRUCTOR_LINK, "Paper", Species.PAPER,
            numberOfPapersValue % 2 == 0 ? Direction.UP : Direction.DOWN);
        FieldLink startingXPositionFieldLink = Links.PAPER_STARTING_X_POSITION_FIELD_LINK.get();
        assertCallEquals(TestUtils.toFittingType(startingXPositionFieldLink.reflection().getType(), 0),
            () -> startingXPositionFieldLink.get(paperInstance),
            emptyContext(),
            r -> "Field startingXPosition in class h04.participants.Paper does not have the correct value");
        assertEquals(2, paperInstance.getNumberOfCoins(), emptyContext(),
            r -> "Paper-Robot does not have the correct amount of coins after initializing");
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    public void testDoVictoryDance(int lanes) {
        int worldSize = 8;
        World.setSize(worldSize, worldSize);
        World.setDelay(0);
        World.setVisible(false);

        Context context = contextBuilder()
            .add("world dimensions (w x h)", "%d x %d".formatted(worldSize, worldSize))
            .add("initial position", "(%d, 0)".formatted(lanes))
            .build();
        Participant paperInstance = callObject(() -> Links.PAPER_CONSTRUCTOR_LINK.get().invoke(lanes, 0), context,
            r -> "An exception occurred while invoking Paper(int, int)");

        call(paperInstance::doVictoryDance, context,
            r -> "An exception occurred while invoking Paper.doVictoryDance()");

        List<Transition.RobotAction> expectedActions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            expectedActions.add(Transition.RobotAction.TURN_LEFT);
        }
        for (int i = 0, deltaX = lanes; i < lanes; i++, deltaX = 0) {
            for (int j = deltaX; j < World.getWidth() - 1; j++) {
                expectedActions.add(Transition.RobotAction.MOVE);
            }
            expectedActions.add(Transition.RobotAction.TURN_LEFT);
            expectedActions.add(Transition.RobotAction.TURN_LEFT);
        }
        List<Transition.RobotAction> actualActions = World.getGlobalWorld()
            .getTrace(paperInstance)
            .getTransitions()
            .stream()
            .filter(transition -> transition.action == Transition.RobotAction.TURN_LEFT || transition.action == Transition.RobotAction.MOVE)
            .sorted(Comparator.comparingInt(transition -> transition.step))
            .map(transition -> transition.action)
            .toList();

        assertEquals(expectedActions, actualActions, context,
            r -> "Paper.doVictoryDance() did not perform the expected actions");
    }

    public enum Outcome {
        WIN(Species.ROCK), DRAW(Species.PAPER), LOSS(Species.SCISSORS);

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
        int worldSize = 2;
        World.setSize(worldSize, worldSize);
        World.setDelay(0);
        World.setVisible(false);

        Context context = contextBuilder()
            .add("world dimensions (w x h)", "%d x %d".formatted(worldSize, worldSize))
            .add("Paper initial position", "(0, 0)")
            .add("opponent initial position", "(1, 0)")
            .add("opponent species", opponentSpecies)
            .build();
        Participant paperInstance = callObject(() -> Links.PAPER_CONSTRUCTOR_LINK.get().invoke(0, 0), context,
            r -> "An exception occurred while invoking Paper(int, int)");

        Participant participantMock = Mockito.mock(Participant.class, Mockito.withSettings()
            .useConstructor(opponentSpecies, 1, 0, Direction.UP)
            .defaultAnswer(Mockito.CALLS_REAL_METHODS));
        Mockito.when(participantMock.fight(Mockito.any())).thenReturn(null);

        Participant victor = callObject(() -> paperInstance.fight(participantMock), context,
            r -> "An exception occurred while invoking Paper.fight(Participant)");
        if (expectedOutcome == Outcome.WIN) {
            assertSame(paperInstance, victor, context,
                    r -> "Paper.fight(Participant) did not return the correct value (itself / 'this')");
            assertTrue(participantMock.isTurnedOff(), context,
                    r -> "Paper.fight(Participant) did not turn off the losing opponent");
        } else {
            assertSame(participantMock, victor, context,
                r -> "Paper.fight(Participant) did not return the correct value (opponent)");
            assertTrue(paperInstance.isTurnedOff(), context,
                r -> "Paper.fight(Participant) did not turn itself off despite losing");
        }
    }
}
