package h04.participants;

import fopbot.Direction;

import java.util.Random;

/**
 * Instances of this class represent participants from the species Paper.
 * <p>
 * Paper spawns with 2 coins.
 */
public class Paper extends Participant {

    /**
     * The initial x coordinate of this robot.
     * Used as a parameter for the victory dance.
     */
    private final int startingXPosition;

    /**
     * Construct paper at the given x,y-coordinate.
     * The orientation is picked at random.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public Paper(int x, int y) {
        super(Species.PAPER, x, y, determineOrientation() ? Direction.UP : Direction.DOWN);
        setNumberOfCoins(2);
        startingXPosition = x;
    }

    /**
     * @return true or false with 50% prob. each.
     */
    private static boolean determineOrientation() {
        Random random = new Random();
        return random.nextBoolean();
    }

    /**
     * Perform the victory dance of a paper.
     * Move in a two-by-two square as many steps as the initial x coordinate.
     */
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
    public Participant fight(Participant opponent) {
        if (opponent.getSpecies() == Species.ROCK) {
            opponent.turnOff();
            return this;
        }

        if (opponent.getSpecies() == Species.SCISSORS) {
            turnOff();
            return opponent;
        }

        if (opponent.getX() > getX()) {
            turnOff();
            return opponent;
        }

        opponent.turnOff();
        return this;
    }
}
