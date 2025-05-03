package h04.participants;

import fopbot.Direction;

public class Rock extends Participant{
    public Rock(int x, int y) {
        super(x, y, Direction.UP);
    }

    @Override
    public void doVictoryDance() {

    }

    @Override
    public int getFacingRobot() {
        return 0;
    }

    @Override
    public boolean isWinning() {
        return false;
    }
}
