package h04.participants;

import fopbot.Robot;
import fopbot.Direction;
import h04.Utils;

public abstract class Participant extends Robot {

    protected final fopbot.Direction orientation;

    public Participant(int x, int y, Direction orientation) {
        super(x, y);
        this.orientation = orientation;
    }

    public abstract void doVictoryDance();

    /*
    -1 = invalid
    0 = Rock
    1 = Paper
    2 = Scissors
     */
    public int getFacingRobot(){
        if(getX() % 2 == 1){
            while(getDirection() != Direction.LEFT){
                turnLeft();
            }
        } else{
            while(getDirection() != Direction.RIGHT){
                turnLeft();
            }
        }
        int facingRobot = Utils.getFacingRobot(this);
        while(getDirection() != orientation){
            turnLeft();
        }
        return facingRobot;
    }

    public abstract boolean isWinning();
}
