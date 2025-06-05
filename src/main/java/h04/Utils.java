package h04;

import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.World;
import h04.participants.Participant;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.List;

@DoNotTouch
public class Utils {

    /**
     * Check the given x coordinate.
     * @param x The x coordinate
     * @return True of the given x coordinate is valid.
     */
    private static boolean checkXCoordinate(int x) {
        return 0 <= x && x < World.getWidth();
    }

    /**
     * Check the given y coordinate.
     * @param y The y coordinate
     * @return True of the given y coordinate is valid.
     */
    private static boolean checkYCoordinate(int y) {
        return 0 <= y && y < World.getHeight();
    }

    /**
     * Find and return a {@link Participant} of the given field.
     *
     * @param x The y coordinate
     * @param y The y coordinate
     *
     * @return A participant on the given field.
     */
    private static Participant getParticipantOn(int x, int y) {
        if (!checkXCoordinate(x) || !checkYCoordinate(y)) {
            return null;
        }

        List<FieldEntity> entities = World.getGlobalWorld().getField(x, y).getEntities();
        for (FieldEntity entity : entities) {
            if (entity instanceof Participant p) {
                return p;
            }
        }

        return null;
    }

    /**
     * Find and return a {@link Participant} the given robot is looking at.
     *
     * @param robot A robot
     *
     * @return A participant on the field the given robot is looking at,
     *  or <code>null</code> of no such participant exists.
     */
    public static Participant getParticipantInFrontOf(Robot robot) {
        if (robot.isFacingUp()) {
            return getParticipantOn(robot.getX(), robot.getY() + 1);
        }

        if (robot.isFacingDown()) {
            return getParticipantOn(robot.getX(), robot.getY() - 1);
        }

        if (robot.isFacingRight()) {
            return getParticipantOn(robot.getX() + 1, robot.getY());
        }

        if (robot.isFacingLeft()) {
            return getParticipantOn(robot.getX() - 1, robot.getY());
        }

        return null;
    }
}
