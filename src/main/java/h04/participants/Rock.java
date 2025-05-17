package h04.participants;

import fopbot.Direction;

/**
 * Instances of this class represent participants from the species Rock.
 */
public class Rock extends Participant {

    /**
     * The number of rounds won by this robot.
     * Used as a parameter for the victory dance.
     */
    public int roundsWon = 0;

    /**
     * Construct rock at the given x,y-coordinate.
     * The orientation is always up.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public Rock(int x, int y) {
        super(Species.ROCK, x, y, Direction.UP);
    }

    /**
     * Perform the victory dance of a rock.
     * Move in a n*n square where n is the number of won rounds.
     */
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
