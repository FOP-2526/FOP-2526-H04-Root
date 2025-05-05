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
    public boolean isWinning() {
        int facingRobot = getFacingRobot();
        if(facingRobot < 0){
            return false;
        }
        if(facingRobot == 2){
            return getX() % 2 == 1;
        }
        if(facingRobot == 0){
            return false;
        }
        return true;
    }
}
