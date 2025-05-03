package h04;

import fopbot.World;
import h04.robots.DoublePowerRobot;
import h04.robots.HackingRobot;
import h04.robots.MovementType;
import h04.robots.VersatileRobot;

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
        // Create a 5x5 world and make it visible
        World.setSize(5, 5);
        World.setVisible(true);

        /*// Create at least one Hacking Robot with different positions and both cases for the array shift
        HackingRobot hackingRobot1 = new HackingRobot(1, 1, true);
        HackingRobot hackingRobot2 = new HackingRobot(2, 2, false);

        // Change the type of the Hacking Robot and check the current and next type
        hackingRobot1.shuffle();
        System.out.println("HackingRobot1 current type: " + hackingRobot1.getType());
        System.out.println("HackingRobot1 next type: " + hackingRobot1.getNextType());

        hackingRobot2.shuffle();
        System.out.println("HackingRobot2 current type: " + hackingRobot2.getType());
        System.out.println("HackingRobot2 next type: " + hackingRobot2.getNextType());

        // Create at least two Versatile Robots with both cases for coordinate exchange
        VersatileRobot versatileRobot1 = new VersatileRobot(1, 2, true, false);
        VersatileRobot versatileRobot2 = new VersatileRobot(3, 4, false, true);

        // Change the type of the Versatile Robot until the type is DIAGONAL and check coordinates
        while (versatileRobot1.getType() != MovementType.DIAGONAL) {
            versatileRobot1.shuffle();
        }
        System.out.println("VersatileRobot1 type is DIAGONAL. x: " + versatileRobot1.getX() + ", y: " + versatileRobot1.getY());

        while (versatileRobot2.getType() != MovementType.DIAGONAL) {
            versatileRobot2.shuffle();
        }
        System.out.println("VersatileRobot2 type is DIAGONAL. x: " + versatileRobot2.getX() + ", y: " + versatileRobot2.getY());

        // Create at least three Double Power Robots and change their types to get all movement types
        DoublePowerRobot doublePowerRobot1 = new DoublePowerRobot(0, 0, true);
        DoublePowerRobot doublePowerRobot2 = new DoublePowerRobot(1, 1, false);
        DoublePowerRobot doublePowerRobot3 = new DoublePowerRobot(2, 2, true);

        // Create a RobotsChallenge with previously created Double Power Robots
        DoublePowerRobot[] robots = {doublePowerRobot1, doublePowerRobot2, doublePowerRobot3};
        RobotsChallenge challenge = new RobotsChallenge(0, 2, robots);

        // Find and display the winning Double Power Robots
        DoublePowerRobot[] winners = challenge.findWinners();
        System.out.println("Winning DoublePowerRobots:");
        for (DoublePowerRobot winner : winners) {
            if (winner != null) {
                //print the winner robot's coordinates
                System.out.println("Winner robot coordinates: x: " + winner.getX() + ", y: " + winner.getY());
            }
        }*/
    }
}
