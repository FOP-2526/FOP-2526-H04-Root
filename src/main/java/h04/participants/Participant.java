package h04.participants;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.RobotFamily;

public abstract class Participant extends Robot {

    private final fopbot.Direction orientation;

    private final Species species;

    public Participant(Species species, int x, int y, Direction orientation) {
        super(x, y);

        this.species = species;
        if (species == Species.PAPER) {
            setRobotFamily(RobotFamily.SQUARE_BLUE);
        } else if (species == Species.SCISSORS) {
            setRobotFamily(RobotFamily.SQUARE_GREEN);
        } else if (species == Species.ROCK) {
            setRobotFamily(RobotFamily.SQUARE_RED);
        }

        this.orientation = orientation;
        while (getDirection() != orientation) {
            turnLeft();
        }
    }

    public abstract void doVictoryDance();

    /**
     * Fights against the given opponent according to the rules of the game.
     * The loser of this match is turned off and the winner is returned.
     * On ties, the left most robot (smaller x coordinate) wins by tie-break.
     *
     * @param opponent The opponent to fight
     * @return Either <code>this</code> or <code>opponent</code>.
     */
    public abstract Participant fight(Participant opponent);

    public Direction getOrientation() {
        return orientation;
    }

    public Species getSpecies() {
        return species;
    }
}
