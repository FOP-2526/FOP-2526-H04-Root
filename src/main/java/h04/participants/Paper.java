package h04.participants;

import fopbot.Direction;

import java.util.Random;

public class Paper extends Participant{

    public Paper(int x, int y) {
        super(x, y, getOrientation() ? Direction.UP : Direction.DOWN);
    }

    private static boolean getOrientation(){
        Random random = new Random();
        return random.nextBoolean();
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
