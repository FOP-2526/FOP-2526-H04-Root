package h04.participants;

import fopbot.Direction;

import java.util.Random;

public class Paper extends Participant{

    public Paper(int x, int y) {
        super(x, y, determineOrientation() ? Direction.UP : Direction.DOWN);
    }

    private static boolean determineOrientation(){
        Random random = new Random();
        return random.nextBoolean();
    }

    @Override
    public void doVictoryDance() {

    }

    @Override
    public boolean isWinning() {
        int facingRobot = getFacingRobot();
        if(facingRobot < 0){
            return false;
        }
        if(facingRobot == 1){
            return getX() % 2 == 0;
        }
        if(facingRobot == 2){
            return false;
        }
        return true;
    }
}
