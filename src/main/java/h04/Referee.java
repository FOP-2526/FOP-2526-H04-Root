package h04;

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

}
