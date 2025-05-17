package h04.participants;

import fopbot.Direction;

public class Scissors extends Participant {

    public Scissors(int x, int y) {
        super(Species.SCISSORS, x, y, Direction.UP);
    }

    @Override
    public void doVictoryDance() {
        while (!isFacingRight()) {
            turnLeft();
        }
        while (hasAnyCoins() && isFrontClear()) {
            putCoin();
            move();
        }
    }

    @Override
    public Participant fight(Participant opponent) {
        if (opponent.getSpecies() == Species.PAPER) {
            opponent.turnOff();
            return this;
        }

        if (opponent.getSpecies() == Species.ROCK) {
            turnOff();
            return opponent;
        }

        if (opponent.getX() < getX()) {
            turnOff();
            return opponent;
        }

        opponent.turnOff();
        return this;
    }
}
