package h04;

import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.World;
import h04.participants.Participant;

import java.util.List;

public class Utils {

    private static boolean checkXCoordinate(int x) {
        return 0 <= x && x < World.getWidth();
    }

    private static boolean checkYCoordinate(int y) {
        return 0 <= y && y < World.getHeight();
    }

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
