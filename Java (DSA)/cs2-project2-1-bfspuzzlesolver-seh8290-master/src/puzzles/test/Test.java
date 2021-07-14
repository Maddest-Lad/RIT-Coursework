package puzzles.test;

import puzzles.solver.Solver;

class Test {

    public static void main(String[] args) {
        // Declare Vars
        TestNode root = new TestNode(1, 3, 2, null);

        Solver solver = new Solver(root);

        solver.BFS();

        solver.printGraph();
        solver.printPath();
    }
}





