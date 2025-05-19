package h04;

import fopbot.Direction;
import fopbot.World;
import h04.participants.*;

/**
 * Instances of the referee class manage a rock paper scissors knock-out tournaments.
 */
public class Referee {

    /**
     * This array holds all participants that have not been eliminated yet.
     */
    private Participant[] participants;

    /**
     * The initial line-up encoded by the representing species.
     */
    private Species[] lineUp;

    /**
     * Create a new referee and pass the line-up as an array of species.
     */
    public Referee(Species... lineUp) {
        this.lineUp = lineUp;
    }

    /**
     * Run the tournament.
     * Create participants from the initial line-up and hold rounds until a single participant wins.
     * The winner is then announced in the console and performs a victory dance.
     */
    public void hostTournament() {
        placeLineUp();

        while (participants.length > 1) {
            doRound();
        }

        Participant winner = participants[0];
        System.out.println("The winner is: " + winner.getSpecies());
        winner.doVictoryDance();
    }

    /**
     * Clear the world.
     * Then, according to the line-up, place new participants for the tournament.
     */
    private void placeLineUp() {
        World.reset();
        participants = new Participant[lineUp.length];

        for (int x = 0; x < lineUp.length; x++) {
            if (lineUp[x] == Species.ROCK) {
                participants[x] = new Rock(x, 0);
            }

            if (lineUp[x] == Species.PAPER) {
                participants[x] = new Paper(x, 0);
            }

            if (lineUp[x] == Species.SCISSORS) {
                participants[x] = new Scissors(x, 0);
            }
        }
    }

    /**
     * Play a single round of the tournament and advance all winning participants.
     */
    private void doRound() {
        Participant[] victors = determineVictors(participants);
        arrangeParticipants(victors);
        participants = victors;
    }

    /**
     * Determines the victors from a given array of participants by simulating matchups.
     * <p>
     * Participants are paired sequentially (i.e., participant at index 0 vs. index 1, 2 vs. 3, etc.).
     * For each pair, save the winner in an array.
     *
     * @param participants An array of {@link Participant}s for this round
     *
     * @return An array of {@link Participant}s representing the victors of each matchup
     */
    private Participant[] determineVictors(Participant[] participants) {
        Participant[] victors;

        if (participants.length % 2 == 0) {
            victors = new Participant[participants.length / 2];
        } else {
            victors = new Participant[participants.length / 2 + 1];
        }

        int victorCount = 0;

        for (int i = 0; i < participants.length; i += 2) {
            victors[victorCount] = playMatchUp(participants[i]);
            victorCount++;
        }

        return victors;
    }

    /**
     * Find the opponent of the given participant and let them fight against one another.
     *
     * @param participant A participant
     *
     * @return The winner of this match
     */
    private Participant playMatchUp(Participant participant) {
        Participant opponent = getOpponent(participant);

        if (opponent == null) {
            // No one left to fight in this round
            return participant;
        }

        return participant.fight(opponent);
    }

    /**
     * Find the opponent of the given participant by looking at their x coordinate.
     * <p>
     * Neighboring participants are paired against each other so the participant
     * with x coordinate <code>x = 2*n</code> always play against <code>x = 2*n + 1</code>.
     * <p>
     * If the number of participates is odd, this will return null for the right most participant
     * because they are left with one to play against.
     *
     * @param participant A participant
     * @return The opponent of the given participant or null
     */
    private Participant getOpponent(Participant participant) {
        if (participant.getX() % 2 == 1) {
            while (participant.getDirection() != Direction.LEFT) {
                participant.turnLeft();
            }
        } else {
            while (participant.getDirection() != Direction.RIGHT) {
                participant.turnLeft();
            }
        }

        Participant opponent = Utils.getParticipantInFrontOf(participant);
        while (participant.getDirection() != participant.getOrientation()) {
            participant.turnLeft();
        }

        return opponent;
    }

    /**
     * Move the given participants to the next round by first moving them up
     * and then as far to the left as possible until they are tightly packed together.
     *
     * @param participants An array of winning participants.
     */
    private void arrangeParticipants(Participant[] participants) {
        ascend(participants);
        for (int i = 0; i < participants.length; i++) {
            moveUp(participants[i]);
        }
    }

    /**
     * Ascend the given participants to the next round.
     *
     * @param participants An array of winning participants.
     */
    private void ascend(Participant[] participants) {
        for (int i = 0; i < participants.length; i++) {
            while (!participants[i].isFacingUp()) {
                participants[i].turnLeft();
            }

            participants[i].move();

            while (participants[i].getDirection() != participants[i].getOrientation()) {
                participants[i].turnLeft();
            }
        }
    }

    /**
     * Move the given participant to the left until they are tightly packed with their neighbor.
     *
     * @param participant An array of winning participants.
     */
    private void moveUp(Participant participant) {
        while (!participant.isFacingLeft()) {
            participant.turnLeft();
        }

        while (participant.isFrontClear() && Utils.getParticipantInFrontOf(participant) == null) {
            participant.move();
        }

        while (participant.getDirection() != participant.getOrientation()) {
            participant.turnLeft();
        }
    }

    /**
     * Set the line-up of the next tournament to be played.
     *
     * @param lineUp An array of species representing the line-up.
     */
    public void setLineUp(Species... lineUp) {
        this.lineUp = lineUp;
    }

}
