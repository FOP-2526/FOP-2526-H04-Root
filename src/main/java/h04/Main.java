package h04;

import fopbot.World;
import h04.participants.Species;

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
        // Create a 8x8 world and make it visible
        World.setSize(8, 8);
        World.setDelay(600);
        World.setVisible(true);

        Referee ref = new Referee(Species.ROCK, Species.PAPER, Species.SCISSORS, Species.ROCK);
        ref.hostTournament();

        ref.setLineUp(Species.PAPER, Species.PAPER, Species.PAPER, Species.SCISSORS);
        ref.hostTournament();

        ref.setLineUp(Species.PAPER, Species.ROCK, Species.PAPER, Species.SCISSORS);
        ref.hostTournament();
    }
}
