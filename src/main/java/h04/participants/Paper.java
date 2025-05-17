package h04.participants;

import fopbot.Direction;
import h04.Utils;

import java.util.Random;

public class Paper extends Participant {

    private final int startingXPosition;

    public Paper(int x, int y) {
        super(x, y, determineOrientation() ? Direction.UP : Direction.DOWN);
        startingXPosition = x;
    }

    private static boolean determineOrientation() {
        Random random = new Random();
        return random.nextBoolean();
    }

    @Override
    public void doVictoryDance() {
        while (!isFacingRight()) {
            turnLeft();
        }
        for (int i = 0; i < startingXPosition; i++) {
            while (isFrontClear()) {
                move();
            }
            turnLeft();
            turnLeft();
        }
    }

    @Override
    public boolean isWinning() {
        int facingRobot = getFacingRobot();
        if (facingRobot < 0) {
            return false;
        }
        if (facingRobot == 1) {
            return getX() % 2 == 1;
        }
        if (facingRobot == 2) {
            return false;
        }
        return true;
    }
}
