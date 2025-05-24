package h04.participants;

import fopbot.*;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Color;

/**
 * Base class for the participants in a rock paper scissors tournament.
 * Participants are descendants of {@link Robot} and fight on one-on-one matchups.
 * If a participant loses, they're turned off.
 * Once a participant wins a tournament, they can perform a victory dance
 * depending on their species.
 */
@DoNotTouch
public abstract class Participant extends Robot {

    public static final RobotFamily ROCK_FAMILY
        = new SvgBasedRobotFamily(
            "ROCK",
            "/Rock.svg",
            "/Rock.off.svg",
            Color.RED
        );

    public static final RobotFamily PAPER_FAMILY
        = new SvgBasedRobotFamily(
            "PAPER",
            "/Paper.svg",
            "/Paper.off.svg",
            Color.BLUE
        );

    public static final RobotFamily SCISSORS_FAMILY
        = new SvgBasedRobotFamily(
            "SCISSORS",
            "/Scissors.svg",
            "/Scissors.off.svg",
            Color.GREEN
        );

    /**
     * The orientation of a participant saved between rounds.
     * This attribute is also used to parameterize the victory dance.
     */
    private final Direction orientation;

    /**
     * The species this participant belongs to.
     */
    private final Species species;

    /**
     * Create a participant of the given species.
     *
     * @param species The species of this participant
     * @param x The x coordinate of this participant
     * @param y The y coordinate of this participant
     * @param orientation The orientation of this participant
     */
    protected Participant(Species species, int x, int y, Direction orientation) {
        super(x, y);
        this.species = species;
        this.orientation = orientation;

        while (getDirection() != orientation) {
            turnLeft();
        }
    }

    /**
     * Makes this participant perform its victory dance
     * depending on its species among other factors.
     */
    public abstract void doVictoryDance();

    /**
     * Fights against the given opponent, according to the rules of the game.
     * The loser of this match is turned off and the winner is returned.
     * On ties, the right most robot (bigger x coordinate) wins by tie-break.
     *
     * @param opponent The opponent to fight
     * @return Either <code>this</code> or <code>opponent</code>.
     */
    public abstract Participant fight(Participant opponent);

    /**
     * @return The orientation of this participant.
     */
    public Direction getOrientation() {
        return orientation;
    }

    /**
     * @return The species of this participant.
     */
    public Species getSpecies() {
        return species;
    }
}
