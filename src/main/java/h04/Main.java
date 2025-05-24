package h04;

import fopbot.World;
import h04.participants.*;

/**
 * Main entry point in executing the program.
 */
public class Main {
    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        // Create an 8 by 8 world and make it visible
        World.setSize(8, 8);

        ParticipantDrawing.register();

        World.setVisible(true);

        Referee ref = new Referee(Species.PAPER, Species.ROCK, Species.PAPER, Species.SCISSORS, Species.ROCK, Species.PAPER, Species.SCISSORS);
        ref.hostTournament();
    }
}
