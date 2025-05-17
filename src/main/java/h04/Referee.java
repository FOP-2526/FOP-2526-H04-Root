package h04;

import fopbot.Direction;
import fopbot.World;
import h04.participants.*;

public class Referee {

    private Participant[] participants;
    private Species[] lineUp;

    public Referee(Species... lineUp) {
        this.lineUp = lineUp;
    }

    public void hostTournament() {
        placeLineUp();

        while (participants.length > 1) {
            doRound();
        }

        Participant winner = participants[0];
        System.out.println("The winner is: " + winner.getSpecies());
        winner.doVictoryDance();
    }

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

    private void doRound() {
        Participant[] victors = determineVictors(participants);
        arrangeParticipants(victors);
        participants = victors;
    }

    private Participant[] determineVictors(Participant[] participants) {
        Participant[] victors = new Participant[participants.length / 2];
        int victorCount = 0;

        for (int i = 0; i < participants.length; i += 2) {
            victors[victorCount] = playMatchUp(participants[i]);
            victorCount++;
        }

        return victors;
    }

    private Participant playMatchUp(Participant participant) {
        Participant opponent = getOpponent(participant);
        return participant.fight(opponent);
    }

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

    private void arrangeParticipants(Participant[] participants) {
        ascend(participants);
        for (int i = 0; i < participants.length; i++) {
            moveUp(participants[i]);
        }
    }

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

    public void setLineUp(Species... lineUp) {
        this.lineUp = lineUp;
    }

}
