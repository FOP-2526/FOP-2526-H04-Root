package h04.participants;

import fopbot.Direction;

/**
 * Instances of this class represent participants from the species Paper.
 * <p>
 * Paper spawns with 2 coins.
 */
public class Paper extends Participant {

    private static int numberOfPapers = 0;

    /**
     * The initial x coordinate of this robot.
     * Used as a parameter for the victory dance.
     */
    private final int startingXPosition;

    /**
     * Construct paper at the given x,y-coordinate.
     * The orientation flip between up and down for each instantiated Paper.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public Paper(int x, int y) {
        super(Species.PAPER, x, y, numberOfPapers++ % 2 == 0 ? Direction.UP : Direction.DOWN);
        setNumberOfCoins(2);
        setRobotFamily(PAPER_FAMILY);
        startingXPosition = x;
    }

    /**
     * Perform the victory dance of a paper.
     * Bounce between the walls as many times as the initial x coordinate.
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
