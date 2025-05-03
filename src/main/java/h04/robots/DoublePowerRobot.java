package h04.robots;

/**
 * Subclass DoublePowerRobot, which inherits from the {@code HackingRobot} class and allows the robot to have two types simultaneously.
 */
public class DoublePowerRobot extends HackingRobot {

    /**
     * Private array doublePowerTypes containing the two types for the DoublePowerRobot.
     */
    private MovementType[] doublePowerTypes = new MovementType[2];

    /**
     * Constructor of the DoublePowerRobot class with parameters x, y, and order.
     * Initializes the robot and assigns two movement types to the robot.
     *
     * @param x     The x-coordinate of the robot.
     * @param y     The y-coordinate of the robot.
     * @param order If true, the movement types are shifted to the right by one index, otherwise to the left by one index.
     */
    public DoublePowerRobot(int x, int y, boolean order) {
        super(x, y, order);

        // Assigning the two types to doublePowerTypes
        doublePowerTypes[0] = getType();
        doublePowerTypes[1] = getNextType();
    }

    /**
     * Overrides the shuffle method of the superclass.
     * Shuffles the robot's type a specified number of times and updates the types in doublePowerTypes.
     *
     * @param itNr The number of iterations to shuffle the type.
     * @return True if the types have changed, false otherwise.
     */
    @Override
    public boolean shuffle(int itNr) {
        boolean changed = super.shuffle(itNr);

        // Updating the types in doublePowerTypes based on the new value of type
        doublePowerTypes[0] = getType();
        doublePowerTypes[1] = getNextType();

        return changed;
    }

    /**
     * Overrides the shuffle method of the superclass.
     * Shuffles the robot's type until the type is different from the current type and updates the types in doublePowerTypes.
     */
    @Override
    public void shuffle() {
        super.shuffle();
        doublePowerTypes[0] = getType();
        doublePowerTypes[1] = getNextType();
    }
}
