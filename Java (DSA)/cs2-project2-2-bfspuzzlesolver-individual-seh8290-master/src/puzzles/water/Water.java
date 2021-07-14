package puzzles.water;

import puzzles.common.solver.*;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class Water {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length < 2)
        {
            System.out.println(("Usage: java Water amount bucket1 bucket2 ..."));
        } else
        {
            // Setup
            WaterNode.amount = Integer.parseInt(args[0]);
            WaterNode.triedConfigs = new LinkedList<>();

            int[] capacities = new int[args.length - 1];

            for (int i = 1; i < args.length; i++)
            {
                capacities[i - 1] = Integer.parseInt(args[i]);
            }

            WaterNode.capacities = capacities;

            WaterNode root = new WaterNode(null, capacities);

            Solver solver = new Solver(root);
            solver.BFS();

            // Write To File (When Console Gets Cut Off)
            //File file = new File("src/log.txt");
            //PrintStream stream = new PrintStream(file);
            //System.setOut(stream);

            solver.printGraph();

            // Printing
            System.out.println("Total configs: " + solver.total);
            System.out.println("Unique configs: " + solver.unique);
            System.out.print("Steps:");
            solver.printPath();

        }
    }
}
