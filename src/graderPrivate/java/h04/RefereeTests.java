package h04;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.World;
import h04.participants.Participant;
import h04.participants.Species;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class RefereeTests {

    private static final BiMap<Object, Participant> DELEGATES = HashBiMap.create();
    private static final Map<Species, MockedConstruction<?>> MOCKED_CONSTRUCTIONS = new EnumMap<>(Species.class);

    private Referee refereeInstance;

    @BeforeAll
    public static void init() {
        Map<Species, Supplier<TypeLink>> typeLinkMapping = Map.of(
            Species.PAPER, Links.PAPER_TYPE,
            Species.ROCK, Links.ROCK_TYPE,
            Species.SCISSORS, Links.SCISSORS_TYPE
        );
        Answer<?> answer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (TestUtils.methodSignatureEquals(invokedMethod, "doVictoryDance")) {
                return null;
            } else if (TestUtils.methodSignatureEquals(invokedMethod, "fight", Participant.class)) {
                return invocation.getMock();
            } else {
                return invokedMethod.invoke(DELEGATES.get(invocation.getMock()), invocation.getRawArguments());
            }
        };

        for (Map.Entry<Species, Supplier<TypeLink>> entry : typeLinkMapping.entrySet()) {
            Species species = entry.getKey();
            TypeLink typeLink = entry.getValue().get();

            if (typeLink != null) {
                AtomicBoolean paperFacesUp = new AtomicBoolean(true);
                MockedConstruction<?> mockedConstruction = Mockito.mockConstruction(typeLink.reflection(),
                    Mockito.withSettings().defaultAnswer(answer),
                    (mock, context) -> {
                        Direction direction;
                        if (species == Species.PAPER) {
                            direction = paperFacesUp.getAndSet(!paperFacesUp.get()) ? Direction.UP : Direction.DOWN;
                        } else {
                            direction = Direction.UP;
                        }
                        @SuppressWarnings("SequencedCollectionMethodCanBeUsed")
                        Participant instance = new Participant(species,
                            (int) context.arguments().get(0),
                            (int) context.arguments().get(1),
                            direction) {
                                @Override
                                public void doVictoryDance() {}

                                @Override
                                public Participant fight(Participant opponent) {
                                    return null;
                                }
                        };
                        DELEGATES.put(mock, instance);
                    });

                MOCKED_CONSTRUCTIONS.put(species, mockedConstruction);
            }
        }
    }

    @BeforeEach
    public void setup() {
        World.setSize(8, 8);
        World.setDelay(0);
        World.setVisible(false);

        refereeInstance = new Referee();
    }

    @AfterAll
    public static void cleanup() {
        MOCKED_CONSTRUCTIONS.forEach((key, mockedConstruction) -> mockedConstruction.close());
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

    @Test
    public void testPlaceLineUp_arrayLength() {
        Species[] species = new Species[] {Species.ROCK, Species.PAPER, Species.SCISSORS, Species.PAPER, Species.ROCK};
        Referee refereeInstance = new Referee(species);
        Context context = contextBuilder()
            .add("lineUp (constructor parameter 1)", species)
            .build();

        call(() -> Links.REFEREE_PLACE_LINE_UP_METHOD_LINK.get().invoke(refereeInstance), context,
            r -> "An exception occurred while invoking Referee.placeLineUp()");
        assertEquals(species.length, Links.REFEREE_PARTICIPANTS_FIELD_LINK.get().<Participant[]>get(refereeInstance).length, context,
            r -> "Length of participant array (field participants) is incorrect");
    }

    @Test
    public void testPlaceLineUp_participantTypes() {
        Species[] species = new Species[] {Species.ROCK, Species.PAPER, Species.SCISSORS, Species.PAPER, Species.ROCK};
        Referee refereeInstance = new Referee(species);
        Context context = contextBuilder()
            .add("lineUp (constructor parameter 1)", species)
            .build();

        call(() -> Links.REFEREE_PLACE_LINE_UP_METHOD_LINK.get().invoke(refereeInstance), context,
            r -> "An exception occurred while invoking Referee.placeLineUp()");
        Participant[] participants = Links.REFEREE_PARTICIPANTS_FIELD_LINK.get().get(refereeInstance);
        for (int i = 0; i < species.length; i++) {
            final int finalI = i;
            assertEquals(species[i], participants[i].getSpecies(), context,
                r -> "Species of participant at index %d is incorrect".formatted(finalI));
        }
    }

    @Test
    public void testPlaceLineUp_orientation() {
        Species[] species = new Species[] {Species.ROCK, Species.PAPER, Species.SCISSORS, Species.PAPER, Species.ROCK};
        Referee refereeInstance = new Referee(species);
        Context context = contextBuilder()
            .add("lineUp (constructor parameter 1)", species)
            .build();

        call(() -> Links.REFEREE_PLACE_LINE_UP_METHOD_LINK.get().invoke(refereeInstance), context,
            r -> "An exception occurred while invoking Referee.placeLineUp()");
        Participant[] participants = Links.REFEREE_PARTICIPANTS_FIELD_LINK.get().get(refereeInstance);
        boolean paperFaceUp = true;
        for (int i = 0; i < species.length; i++) {
            final int finalI = i;
            assertEquals(i, participants[i].getX(), context,
                r -> "X coordinate of participant at index %d is incorrect".formatted(finalI));
            assertEquals(0, participants[i].getY(), context,
                r -> "Y coordinate of participant at index %d is incorrect".formatted(finalI));
            if (species[i] != Species.PAPER) {
                assertEquals(Direction.UP, participants[i].getDirection(), context,
                    r -> "Direction of participant at index %d is incorrect".formatted(finalI));
            } else {
                assertEquals(paperFaceUp ? Direction.UP : Direction.DOWN, participants[i].getDirection(), context,
                    r -> "Direction of participant at index %d is incorrect".formatted(finalI));
                paperFaceUp = !paperFaceUp;
            }
        }
    }

    @Test
    @SuppressWarnings("SuspiciousMethodCalls")
    public void testPlaceLineUp_callsReset() {
        Species[] species = new Species[] {Species.ROCK, Species.PAPER, Species.SCISSORS, Species.PAPER, Species.ROCK};
        Referee refereeInstance = new Referee(species);
        Context context = contextBuilder()
            .add("lineUp (constructor parameter 1)", species)
            .build();

        new Robot(5, 5);
        call(() -> Links.REFEREE_PLACE_LINE_UP_METHOD_LINK.get().invoke(refereeInstance), context,
            r -> "An exception occurred while invoking Referee.placeLineUp()");
        Participant[] participants = Links.REFEREE_PARTICIPANTS_FIELD_LINK.get().get(refereeInstance);
        List<? extends FieldEntity> fieldEntities = World.getGlobalWorld()
            .getAllFieldEntities()
            .stream()
            // Need to get the "inverse delegate" since the delegate is placed in the world but the mock isn't
            .map(fieldEntity -> DELEGATES.containsValue(fieldEntity) ? (FieldEntity) DELEGATES.inverse().get(fieldEntity) : fieldEntity)
            .toList();
        assertTrue(Set.of(participants).containsAll(fieldEntities), context,
            r -> "Referee.placeLineUp() did not call World.reset()");
    }

    @Test
    public void testGetOpponent_left() {
        Participant[] participants = makeParticipantMocks(2);
        Context context = contextBuilder()
            .add("all participants", participants)
            .add("participant (parameter 1)", participants[1])
            .build();

        Participant result = callObject(() -> Links.REFEREE_GET_OPPONENT_METHOD_LINK.get().invoke(refereeInstance, participants[1]), context,
            r -> "An exception occurred while invoking Referee.getOpponent(Participant)");
        assertSame(participants[0], result, context,
            r -> "Referee.getOpponent(Participant) did not return the correct value");
    }

    @Test
    public void testGetOpponent_right() {
        Participant[] participants = makeParticipantMocks(2);
        Context context = contextBuilder()
            .add("all participants", participants)
            .add("participant (parameter 1)", participants[0])
            .build();

        Participant result = callObject(() -> Links.REFEREE_GET_OPPONENT_METHOD_LINK.get().invoke(refereeInstance, participants[0]), context,
            r -> "An exception occurred while invoking Referee.getOpponent(Participant)");
        assertSame(participants[1], result, context,
            r -> "Referee.getOpponent(Participant) did not return the correct value");
    }

    @Test
    public void testGetOpponent_none() {
        Participant[] participants = makeParticipantMocks(1);
        Context context = contextBuilder()
            .add("all participants", participants)
            .add("participant (parameter 1)", participants[0])
            .build();

        Participant result = callObject(() -> Links.REFEREE_GET_OPPONENT_METHOD_LINK.get().invoke(refereeInstance, participants[0]), context,
            r -> "An exception occurred while invoking Referee.getOpponent(Participant)");
        assertNull(result, context,
            r -> "Referee.getOpponent(Participant) did not return the correct value");
    }

    @Test
    public void testGetOpponent_orientation() {
        Participant[] participants = makeParticipantMocks(2);
        Participant[] originalParticipants = Arrays.copyOf(participants, participants.length);

        for (int i = 0; i < participants.length; i++) {
            final int finalI = i;
            Context context = contextBuilder()
                .add("all participants", participants)
                .add("participant (parameter 1)", participants[i])
                .build();

            call(() -> Links.REFEREE_GET_OPPONENT_METHOD_LINK.get().invoke(refereeInstance, participants[finalI]), context,
                r -> "An exception occurred while invoking Referee.getOpponent(Participant)");
            assertEquals(i, originalParticipants[i].getX(), context,
                r -> "Referee.getOpponent(Participant) moved the participant from its position");
            assertEquals(0, originalParticipants[i].getY(), context,
                r -> "Referee.getOpponent(Participant) moved the participant from its position");
            assertEquals(originalParticipants[i].getOrientation(), originalParticipants[i].getDirection(), context,
                r -> "Referee.getOpponent(Participant) did not make the participant face its default direction");
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
