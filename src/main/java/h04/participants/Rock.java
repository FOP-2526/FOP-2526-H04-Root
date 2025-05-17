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
    public Participant fight(Participant opponent) {
        if (opponent.getSpecies() == Species.SCISSORS) {
            roundsWon++;
            opponent.turnOff();
            return this;
        }

        if (opponent.getSpecies() == Species.PAPER) {
            turnOff();
            return opponent;
        }

        if (getX() < opponent.getX()) {
            roundsWon++;
            opponent.turnOff();
            return this;
        }

        turnOff();
        return opponent;
    }
}
