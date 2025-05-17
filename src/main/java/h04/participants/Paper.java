package h04.participants;

import fopbot.Direction;

import java.util.Random;

public class Paper extends Participant {

    private final int startingXPosition;

    public Paper(int x, int y) {
        super(Species.PAPER, x, y, determineOrientation() ? Direction.UP : Direction.DOWN);
        startingXPosition = x;
    }

    private static boolean determineOrientation() {
        Random random = new Random();
        return random.nextBoolean();
    }

    @Override
    public void doVictoryDance() {
        while (!isFacingRight()) {
            turnLeft();
        }
        for (int i = 0; i < startingXPosition; i++) {
            while (isFrontClear()) {
                move();
            }
            turnLeft();
            turnLeft();
        }
    }

    @Override
    public Participant fight(Participant other) {
        if (other.getSpecies() == Species.ROCK) {
            return this;
        }

        if (other.getSpecies() == Species.SCISSORS) {
            return this;
        }

        if (other.getX() < getX()) {
            return other;
        }

        return this;
    }
}
