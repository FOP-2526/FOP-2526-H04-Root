package h04.participants;

import fopbot.Robot;
import fopbot.Direction;
import fopbot.RobotFamily;
import h04.Utils;

import java.util.HashMap;
import java.util.Map;

public abstract class Participant extends Robot {

    private final static Map<Class<? extends Participant>, RobotFamily> designs = new HashMap<>();

    protected final fopbot.Direction orientation;

    static {
        designs.put(Rock.class, RobotFamily.SQUARE_RED);
        designs.put(Paper.class, RobotFamily.SQUARE_BLUE);
        designs.put(Scissors.class, RobotFamily.SQUARE_GREEN);
    }

    public Participant(int x, int y, Direction orientation) {
        super(x, y);
        setRobotFamily(designs.get(this.getClass()));
        this.orientation = orientation;
        while(getDirection() != orientation){
            turnLeft();
        }
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

    public int getFacingRobot(Direction orientation){
        while(getDirection() != orientation){
            turnLeft();
        }
        int facingRobot = Utils.getFacingRobot(this);
        while(getDirection() != orientation){
            turnLeft();
        }
        return facingRobot;
    }

    public abstract boolean isWinning();

    public Direction getOrientation(){
        return orientation;
    }
}
