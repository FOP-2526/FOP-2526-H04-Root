package h04.robots;

import fopbot.Robot;
import java.util.Random;

/**
 * The HackingRobot class extends the Robot class and provides additional methods for movement in the grid.
 * The robot can have different types of movements which can be shuffled.
 */
public class HackingRobot extends Robot {

    /**
     * Private array "robotTypes" containing the elements of the enumeration MovementType in reverse alphabetical order.
     */
    private MovementType[] robotTypes = {MovementType.TELEPORT, MovementType.OVERSTEP, MovementType.DIAGONAL};

    /**
     * Private variable that contains the type of the robot.
     */
    private MovementType type;

    /**
     * Constructs a new HackingRobot at the specified coordinates.
     * The order parameter determines the initial order of the movement types.
     *
     * @param x     The x-coordinate of the robot.
     * @param y     The y-coordinate of the robot.
     * @param order If true, the movement types are shifted to the right by one index, otherwise to the left by one index.
     */
    public HackingRobot(int x, int y, boolean order) {
        super(x, y);

        if (order) {
            // Move elements to the right by 1 index
            MovementType lastElement = robotTypes[robotTypes.length - 1];
            for (int i = robotTypes.length - 1; i > 0; i--) {
                robotTypes[i] = robotTypes[i - 1];
            }
            robotTypes[0] = lastElement;
        } else {
            // Move elements to the left by 1 index
            MovementType firstElement = robotTypes[0];
            for (int i = 0; i < robotTypes.length - 1; i++) {
                robotTypes[i] = robotTypes[i + 1];
            }
            robotTypes[robotTypes.length - 1] = firstElement;
        }

        this.type = robotTypes[0];
    }

    /**
     * Returns the current type of the robot.
     *
     * @return The current MovementType of the robot.
     */
    public MovementType getType() {
        return type;
    }

    /**
     * Returns the movement type located 1 index to the right of the current type of the robot.
     *
     * @return The next MovementType of the robot.
     */
    public MovementType getNextType() {
        int currentIndex = -1;
        for (int i = 0; i < robotTypes.length; i++) {
            if (robotTypes[i] == type) {
                currentIndex = i;
                break;
            }
        }
        return robotTypes[(currentIndex + 1) % robotTypes.length];
    }

    /**
     * Generates a random number between zero (inclusive) and the specified limit (exclusive).
     *
     * @param limit The upper bound (exclusive) for the random number.
     * @return A random integer between 0 (inclusive) and the specified limit (exclusive).
     */
    public int getRandom(int limit) {
        Random random = new Random();
        return random.nextInt(limit);
    }

    /**
     * Randomly changes the type of the robot a specified number of times.
     *
     * @param itNr The number of iterations to shuffle the type.
     * @return True if the type changed after shuffling, false otherwise.
     */
    public boolean shuffle(int itNr) {
        MovementType previousType = this.type;
        for (int i = 0; i < itNr; i++) {
            int randomIndex = getRandom(robotTypes.length);
            this.type = robotTypes[randomIndex];
        }

        return this.type != previousType;
    }

    /**
     * Randomly changes the type of the robot until the type is different from the current type.
     */
    public void shuffle() {
        while (!shuffle(1)) {
        }
    }
}
