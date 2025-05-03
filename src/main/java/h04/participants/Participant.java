package h04.participants;

import fopbot.Robot;
import fopbot.Direction;

public abstract class Participant extends Robot {

    protected fopbot.Direction orientation;

    public Participant(int x, int y, Direction orientation) {
        super(x, y);
        this.orientation = orientation;
    }

    public abstract void doVictoryDance();

    public int getFacingRobot(){
        return 0;
    }

    public abstract boolean isWinning();
}
