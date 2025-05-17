package h04.participants;

import fopbot.Direction;

public class Rock extends Participant {

    public int roundsWon = 0;

    public Rock(int x, int y) {
        super(Species.ROCK, x, y, Direction.UP);
    }

    @Override
    public void doVictoryDance() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < roundsWon && isFrontClear(); j++) {
                move();
            }
            turnLeft();
            turnLeft();
            turnLeft();
        }
    }

    @Override
    public Participant fight(Participant other) {
        if (other.getSpecies() == Species.SCISSORS) {
            roundsWon++;
            return this;
        }

        if (other.getSpecies() == Species.PAPER) {
            return other;
        }

        if (getX() < other.getX()) {
            roundsWon++;
            return this;
        }

        return other;
    }
}
