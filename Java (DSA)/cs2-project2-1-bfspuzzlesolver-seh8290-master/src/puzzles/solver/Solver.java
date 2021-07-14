package puzzles.solver;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class Solver {

    // BFS Member Vars
    public Queue<Node> queue;
    public LinkedList<Node> visited;

    // Visited Stats
    public int unique;
    public int total;

    // Node Tracking (Goal is Only Important For the Fancy Printing)
    private Node root;
    private Node goal;

    // Success
    private boolean successful;

    public Solver(Node root) {
        this.unique = 1; // Start at 1 to Count Root
        this.total = 1;
        this.queue = new LinkedList<>();
        this.visited = new LinkedList<>();
        this.root = root;
    }

    public void BFS() {

        // Setup Starting Configuration
        root.addAdjacent(root.generateChildren());
        queue.addAll(root.getAdjacent());
        visited.add(root);

        while (!queue.isEmpty())
        {
            Node current = queue.poll(); // @Not Null

            if (current.isGoal())
            {
                goal = current;
                visited.add(current);
                successful = true;
                return;
            }

            // Add New Children
            current.addAdjacent(current.generateChildren());

            // Look For Unvisited Adjacent (Child) Nodes
            for (Node node : current.getAdjacent())
            {
                total++;
                if (!visited.contains(node))
                {
                    queue.offer(node);
                    visited.add(node);
                    this.unique++;
                }
            }
            visited.add(current);
        }

        // Might Need To Return The Path Instead, Not Sure Yet
        successful = false;
    }

    //The Fancy Printing Methods
    public void printGraph() {
        root.printGraph(false);
    }

    public void printPath() {
        if(successful)
            goal.printGraph(true);
        else
            System.out.println("No Solution Found");
    }
}
