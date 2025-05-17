package h04.participants;

import fopbot.Direction;
import h04.Utils;

public class Rock extends Participant {

    public int wonRounds = 0;

    public Rock(int x, int y) {
        super(x, y, Direction.UP);
    }

    @Override
    public void doVictoryDance() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < wonRounds && isFrontClear(); j++) {
                move();
            }
            turnLeft();
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
        if (facingRobot == 0) {
            if (getX() % 2 == 1) {
                wonRounds += 1;
                return true;
            }
            return false;
        }
        if (facingRobot == 1) {
            return false;
        }
        wonRounds += 1;
        return true;
    }
}
