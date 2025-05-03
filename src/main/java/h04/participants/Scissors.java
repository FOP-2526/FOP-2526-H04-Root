package h04.participants;

import fopbot.Direction;

public class Scissors extends Participant{
    public Scissors(int x, int y) {
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
