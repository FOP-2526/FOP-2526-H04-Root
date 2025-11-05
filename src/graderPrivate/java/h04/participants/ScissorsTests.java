package h04.participants;

import fopbot.Coin;
import fopbot.Direction;
import fopbot.World;
import h04.Links;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.awt.*;
import java.util.Map;
import java.util.stream.IntStream;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class ScissorsTests extends AbstractParticipantTests {

    @Test
    public void testDefinition() {
        super.testDefinition(Links.SCISSORS_TYPE, "Scissors");
    }

    @Test
    public void testConstructor() {
        super.testConstructor(Links.SCISSORS_CONSTRUCTOR_LINK, "Scissors", Species.SCISSORS, Direction.UP);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 8})
    public void testDoVictoryDance(int worldSize) {
        World.setSize(worldSize, worldSize);
        World.setDelay(0);
        World.setVisible(false);

        int initialCoins = 5;
        Context context = contextBuilder()
            .add("world dimensions (w x h)", "%d x %d".formatted(worldSize, worldSize))
            .add("initial position", "(0, 0)")
            .add("initial coins", initialCoins)
            .build();
        Participant scissorInstance = callObject(() -> Links.SCISSORS_CONSTRUCTOR_LINK.get().invoke(0, 0), context,
            r -> "An exception occurred while invoking Scissors(int, int)");
        scissorInstance.setNumberOfCoins(initialCoins);

        call(scissorInstance::doVictoryDance, context,
            r -> "An exception occurred while invoking Scissors.doVictoryDance()");
        assertEquals(Math.min(worldSize - 1, initialCoins), scissorInstance.getX(), context,
            r -> "Scissors.doVictoryDance() did not move the robot to the correct X coordinate");
        assertEquals(0, scissorInstance.getY(), context,
            r -> "Scissors.doVictoryDance() moved the robot from its Y coordinate");
        assertEquals(Direction.RIGHT, scissorInstance.getDirection(), context,
            r -> "Scissors.doVictoryDance() did make the robot face the correct direction");
        assertEquals(Math.max(initialCoins - worldSize + 1, 0), scissorInstance.getNumberOfCoins(), context,
            r -> "Scissors.doVictoryDance() did not make the robot place the correct number of coins");
        IntStream.range(0, Math.min(worldSize - 1, initialCoins))
            .mapToObj(x -> Map.entry(new Point(x, 0), World.getGlobalWorld()
                .getField(x, 0)
                .getEntities()
                .stream()
                .filter(fieldEntity -> fieldEntity instanceof Coin)
                .map(Coin.class::cast)
                .findAny()
                .map(Coin::getCount)
                .orElse(0)))
            .forEach(entry -> assertEquals(1, entry.getValue(), context,
                r -> "Scissors.doVictoryDance() did not make the robot place exactly one coin at (%d, %d)"
                    .formatted(entry.getKey().x, entry.getKey().y)));
    }

    public enum Outcome {
        WIN(Species.PAPER), DRAW(Species.SCISSORS), LOSS(Species.ROCK);

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

        int initialCoins = 5;
        Context context = contextBuilder()
            .add("world dimensions (w x h)", "%d x %d".formatted(worldSize, worldSize))
            .add("Scissor initial position", "(0, 0)")
            .add("Scissor initial coins", 0)
            .add("opponent initial position", "(1, 0)")
            .add("opponent species", opponentSpecies)
            .add("opponent initial coins", initialCoins)
            .build();
        Participant scissorInstance = callObject(() -> Links.SCISSORS_CONSTRUCTOR_LINK.get().invoke(0, 0), context,
            r -> "An exception occurred while invoking Scissors(int, int)");

        Participant participantMock = Mockito.mock(Participant.class, Mockito.withSettings()
            .useConstructor(opponentSpecies, 1, 0, Direction.UP)
            .defaultAnswer(Mockito.CALLS_REAL_METHODS));
        Mockito.when(participantMock.fight(Mockito.any())).thenReturn(null);
        participantMock.setNumberOfCoins(initialCoins);

        Participant victor = callObject(() -> scissorInstance.fight(participantMock), context,
            r -> "An exception occurred while invoking Scissors.fight(Participant)");
        if (expectedOutcome == Outcome.WIN) {
            assertSame(scissorInstance, victor, context,
                r -> "Scissors.fight(Participant) did not return the correct value (itself / 'this')");
        } else {
            assertSame(participantMock, victor, context,
                r -> "Scissors.fight(Participant) did not return the correct value (opponent)");
        }
        if (opponentSpecies != Species.SCISSORS) {
            assertEquals(initialCoins, scissorInstance.getNumberOfCoins(), context,
                r -> "Scissors.fight(Participant) did not steal the correct amount of coins from its opponent");
            assertEquals(0, participantMock.getNumberOfCoins(), context,
                r -> "Scissors.fight(Participant) did not set the opponent's number of coins to 0");
        } else {
            assertEquals(0, scissorInstance.getNumberOfCoins(), context,
                r -> "Scissors.fight(Participant) was not supposed to steal any coins");
            assertEquals(initialCoins, participantMock.getNumberOfCoins(), context,
                r -> "Scissors.fight(Participant) stole coins from the opponent");
        }
    }
}
