package h04;

import fopbot.FieldEntity;
import fopbot.KarelWorld;
import fopbot.Robot;
import fopbot.World;
import h04.participants.Paper;
import h04.participants.Participant;
import h04.participants.Rock;
import h04.participants.Scissors;

import java.util.List;

public class Utils {
    private static boolean checkXCoordinate(int x, KarelWorld karelWorld) {
        return x <= karelWorld.getWidth() - 1 && x >= 0;
    }

    private static boolean checkYCoordinate(int y, KarelWorld karelWorld) {
        return y <= karelWorld.getHeight() - 1 && y >= 0;
    }

    public static boolean isFacingWall(Robot robot) {
        KarelWorld karelWorld = World.getGlobalWorld();
        switch (robot.getDirection()) {
            case UP -> {
                return !checkYCoordinate(robot.getY() + 1, karelWorld);
            }
            case RIGHT -> {
                return !checkXCoordinate(robot.getX() + 1, karelWorld);
            }
            case DOWN -> {
                return !checkYCoordinate(robot.getY() - 1, karelWorld);
            }
            case LEFT -> {
                return !checkXCoordinate(robot.getX() - 1, karelWorld);
            }
        }
        return false;
    }

    private static int getFacingRobotChecked(Robot robot, KarelWorld karelWorld, int x, int y) {
        if (!(checkXCoordinate(x, karelWorld) && checkYCoordinate(y, karelWorld))) {
            return -1;
        }
        List<FieldEntity> entities = karelWorld.getField(x, y).getEntities();
        Participant found = null;
        for (FieldEntity entity : entities) {
            if (entity instanceof Participant p) {
                found = p;
                break;
            }
        }
        if (found == null) {
            return -1;
        }
        if (found instanceof Rock) {
            return 0;
        }
        if (found instanceof Paper) {
            return 1;
        }
        if (found instanceof Scissors) {
            return 2;
        }
        return -1;
    }

    public static int getFacingRobot(Robot robot) {
        KarelWorld karelWorld = World.getGlobalWorld();
        if (robot.isFacingUp()) {
            return getFacingRobotChecked(robot, karelWorld, robot.getX(), robot.getY() + 1);
        }
        if (robot.isFacingRight()) {
            return getFacingRobotChecked(robot, karelWorld, robot.getX() + 1, robot.getY());
        }
        if (robot.isFacingDown()) {
            return getFacingRobotChecked(robot, karelWorld, robot.getX(), robot.getY() - 1);
        }
        return getFacingRobotChecked(robot, karelWorld, robot.getX() - 1, robot.getY());
    }
}
