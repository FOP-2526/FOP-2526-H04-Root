package h04;

import fopbot.Direction;
import h04.participants.Paper;
import h04.participants.Participant;
import h04.participants.Rock;
import h04.participants.Scissors;

public class Referee {
    Participant[] participants;
    int[] setup;

    public Referee(int[] setup){
        this.setup = setup;
        initialize();
    }

    private void initialize(){
        participants = new Participant[setup.length];
        for(int i=0;i<setup.length;i++){
            switch(setup[i]){
                case 0 -> {
                    participants[i] = new Rock(i, 0);
                }
                case 1 -> {
                    participants[i] = new Paper(i, 0);
                }
                case 2 -> {
                    participants[i] = new Scissors(i, 0);
                }
            }
        }
    }

    private Participant[] determineVictors(Participant[] participants){
        Participant[] victors = new Participant[participants.length/2];
        int victorCount = 0;
        for(int i=0;i< participants.length;i++){
            if(participants[i].isWinning()){
                victors[victorCount] = participants[i];
                victorCount++;
            }
        }
        return victors;
    }

    private void moveUp(Participant participant){
        while(!(participant.isFacingLeft())){
            participant.turnLeft();
        }
        while(participant.getFacingRobot(Direction.LEFT) < 0 && participant.isFrontClear()){
            participant.move();
        }
        while(participant.getDirection() != participant.getOrientation()){
            participant.turnLeft();
        }
    }

    private void ascend(Participant[] participants){
        for(int i=0;i<participants.length;i++){
            while(!(participants[i].isFacingUp())){
                participants[i].turnLeft();
            }
            participants[i].move();
            while(participants[i].getDirection() != participants[i].getOrientation()){
                participants[i].turnLeft();
            }
        }
    }

    private void arrangeParticipants(Participant[] participants){
        ascend(participants);
        for(int i=0;i<participants.length;i++){
            moveUp(participants[i]);
        }
    }

    public void doRound(){
        Participant[] victors = determineVictors(participants);
        arrangeParticipants(victors);
        participants = victors;
    }

}
