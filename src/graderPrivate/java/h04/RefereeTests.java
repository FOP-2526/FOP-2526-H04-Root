package h04;

import fopbot.Direction;
import fopbot.World;
import h04.participants.Participant;
import h04.participants.Species;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.Arrays;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class RefereeTests {

    private Referee refereeInstance;

    @BeforeEach
    public void setup() {
        World.setSize(8, 8);
        World.setDelay(0);
        World.setVisible(false);

        refereeInstance = new Referee();
    }

    @Test
    public void testAscend_movesUp() {
        Participant[] participants = makeParticipantMocks(5);
        Context context = contextBuilder()
            .add("participants (parameter 1)", participants)
            .build();

        call(() -> Links.REFEREE_ASCEND_METHOD_LINK.get().invoke(refereeInstance, (Object) participants), context,
            r -> "An exception occurred while invoking Referee.ascend(Participant[])");
        for (int x = 0; x < participants.length; x++) {
            final int finalX = x;
            assertEquals(x, participants[x].getX(), context,
                r -> "Participant at index %d was moved from its original X coordinate".formatted(finalX));
            assertEquals(1, participants[x].getY(), context,
                r -> "Participant at index %d was not moved exactly one field up".formatted(finalX));
        }
    }

    @Test
    public void testAscend_orientation() {
        Participant[] participants = makeParticipantMocks(5);
        Participant[] originalParticipants = Arrays.copyOf(participants, participants.length);
        Context context = contextBuilder()
            .add("participants (parameter 1)", participants)
            .build();

        call(() -> Links.REFEREE_ASCEND_METHOD_LINK.get().invoke(refereeInstance, (Object) participants), context,
            r -> "An exception occurred while invoking Referee.ascend(Participant[])");
        for (int i = 0; i < participants.length; i++) {
            final int finalI = i;
            assertSame(originalParticipants[i], participants[i], context,
                r -> "Participant at index %d is not at its original array index".formatted(finalI));
            assertEquals(originalParticipants[i].getOrientation(), participants[i].getDirection(), context,
                r -> "Participant at index %d does not face the direction that is returned by Participant.getOrientation()"
                    .formatted(finalI));
        }
    }

    @Test
    public void testMoveUp_leftOnly() {
        Participant participant = makeParticipantMocks(1)[0];
        Context context = contextBuilder()
            .add("participant (parameter 1)", participant)
            .build();

        participant.setField(5, 0);
        call(() -> Links.REFEREE_MOVE_UP_METHOD_LINK.get().invoke(refereeInstance, participant), context,
            r -> "An exception occurred while invoking Referee.moveUp(Participant)");
        assertEquals(0, participant.getX(), context,
            r -> "Referee.moveUp(Participant) did not move the participant to the left-most position");
        assertEquals(0, participant.getY(), context,
            r -> "Referee.moveUp(Participant) moved the participant from its Y coordinate");
    }

    @Test
    public void testMoveUp_noSpaces() {
        Participant[] participants = makeParticipantMocks(2);
        Context context = contextBuilder()
            .add("participant (parameter 1, call 1)", participants[0])
            .add("participant (parameter 1, call 2)", participants[1])
            .build();

        participants[0].setField(2, 0);
        participants[1].setField(5, 0);
        for (int i = 0; i < participants.length; i++) {
            final int finalI = i;
            call(() -> Links.REFEREE_MOVE_UP_METHOD_LINK.get().invoke(refereeInstance, participants[finalI]), context,
                r -> "An exception occurred while invoking Referee.moveUp(Participant)");
            assertEquals(i, participants[i].getX(), context,
                r -> "Referee.moveUp(Participant) did not move the participant to the left-most possible position");
            assertEquals(0, participants[i].getY(), context,
                r -> "Referee.moveUp(Participant) moved the participant from its Y coordinate");
        }
    }

    @Test
    public void testMoveUp_orientation() {
        Participant[] participants = makeParticipantMocks(2);
        Participant[] originalParticipants = Arrays.copyOf(participants, participants.length);

        participants[0].setField(2, 0);
        participants[1].setField(5, 0);
        for (int i = 0; i < participants.length; i++) {
            final int finalI = i;
            Context context = contextBuilder()
                .add("participant (parameter 1)", participants[i])
                .build();
            call(() -> Links.REFEREE_MOVE_UP_METHOD_LINK.get().invoke(refereeInstance, participants[finalI]), context,
                r -> "An exception occurred while invoking Referee.moveUp(Participant)");
            assertEquals(originalParticipants[i].getOrientation(), participants[i].getDirection(), context,
                r -> "Participant does not face the direction that is returned by Participant.getOrientation()");
        }
    }

    private Participant[] makeParticipantMocks(int amount) {
        Participant[] result = new Participant[amount];
        Species[] species = Species.values();
        Direction[] directions = Direction.values();

        for (int i = 0; i < amount; i++) {
            Participant participant = Mockito.mock(Participant.class, Mockito.withSettings()
                .useConstructor(species[i % species.length], i, 0, directions[i % directions.length])
                .defaultAnswer(Mockito.CALLS_REAL_METHODS));
            result[i] = participant;
        }

        return result;
    }
}
