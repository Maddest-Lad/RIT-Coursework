package puzzles.clock;

import puzzles.common.solver.*;


public class Clock {

    public static void main(String[] args) {
        if (args.length != 3)
        {
            System.out.println("Usage: java Clock hours start stop");
        }

        int hours = Integer.parseInt(args[0]);
        int start = Integer.parseInt(args[1]);
        int end = Integer.parseInt(args[2]);

        if(!(start > hours))
        {
            ClockNode.hoursCap = hours;
            ClockNode.endTime = end;

            ClockNode root = new ClockNode(start, null);

            Solver solver = new Solver(root);
            solver.BFS();

            // Printing
            System.out.println("Hours: " + hours + ", Start: " + start + ", End: " + end);
            System.out.println("Total configs: " + solver.total);
            System.out.println("Unique configs: " + solver.unique);
            System.out.print("Steps:");
            solver.printPath();
        } else {
            System.out.println("No Solution");
        }

    }
}