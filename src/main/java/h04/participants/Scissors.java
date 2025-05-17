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
    public Participant fight(Participant other) {
        if (other.getSpecies() == Species.PAPER) {
            return this;
        }

        if (other.getSpecies() == Species.ROCK) {
            return other;
        }

        if (other.getX() < getX()) {
            return other;
        }

        return this;
    }
}
