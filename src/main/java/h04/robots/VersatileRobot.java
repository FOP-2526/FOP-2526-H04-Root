package h04.robots;

/**
 * Subclass VersatileRobot, which inherits from the class {@code HackingRobot}.
 * This robot can switch its coordinates and has specific behavior when its type is DIAGONAL.
 */
public class VersatileRobot extends HackingRobot {

    /**
     * Constructor of the VersatileRobot class with the parameters x, y, order, and exchange.
     * Initializes the robot and optionally exchanges its coordinates.
     *
     * @param x        The x-coordinate of the robot.
     * @param y        The y-coordinate of the robot.
     * @param order    If true, the movement types are shifted to the right by one index, otherwise to the left by one index.
     * @param exchange If true, the coordinates x and y are exchanged.
     */
    public VersatileRobot(int x, int y, boolean order, boolean exchange) {
        super(x, y, order);

        if (exchange) {
            int aux = x;
            setX(y);
            setY(aux);
        }

        if (getType() == MovementType.DIAGONAL) {
            setY(getX());
        }
    }

    /**
     * Overrides the shuffle method of the superclass.
     * Shuffles the robot's type a specified number of times and adjusts the y-coordinate if the type is DIAGONAL.
     *
     * @param itNr The number of iterations to shuffle the type.
     * @return True if the types have changed, false otherwise.
     */
    @Override
    public boolean shuffle(int itNr) {
        boolean changed = super.shuffle(itNr);
        if (getType() == MovementType.DIAGONAL) {
            setY(getX());
        }
        return changed;
    }

    /**
     * Overrides the shuffle method of the superclass.
     * Shuffles the robot's type until the type is different from the current type and adjusts the y-coordinate if the type is DIAGONAL.
     */
    @Override
    public void shuffle() {
        super.shuffle();
        if (getType() == MovementType.DIAGONAL) {
            setY(getX());
        }
    }
}
