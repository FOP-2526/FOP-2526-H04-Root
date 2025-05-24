package h04.participants;

import fopbot.Direction;

/**
 * Instances of this class represent participants from the species of scissors.
 * <p>
 * Scissors start with no coins but steal coins when fighting against anything other than a pair of scissors.
 */
public class Scissors extends Participant {

    /**
     * Construct scissors at the given x,y-coordinate.
     * The orientation is always up.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public Scissors(int x, int y) {
        super(Species.SCISSORS, x, y, Direction.UP);
        setRobotFamily(SCISSORS_FAMILY);
    }

    /**
     * Perform the victory dance of a rock.
     * Place all coins this robot has in a line to the right.
     */
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
        if (opponent.getSpecies() != Species.SCISSORS) {
            setNumberOfCoins(opponent.getNumberOfCoins() + getNumberOfCoins());
            opponent.setNumberOfCoins(0);
        }

        if (opponent.getSpecies() == Species.PAPER) {
            opponent.turnOff();
            return this;
        }

        if (opponent.getSpecies() == Species.ROCK) {
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
