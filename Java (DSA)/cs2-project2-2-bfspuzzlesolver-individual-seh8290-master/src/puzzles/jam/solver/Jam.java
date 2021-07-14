package puzzles.jam.solver;

import puzzles.common.solver.Solver;
import puzzles.jam.model.Car;
import puzzles.jam.model.JamNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Jam {

    public static String EMPTY_SPACE = ".";

    public static JamNode readFileToGameStatic(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(args[0]));

        // Read In Board Size & Number of Cars
        var boardSize = reader.readLine().split(" ");
        int rows = Integer.parseInt(boardSize[0]);
        int cols = Integer.parseInt(boardSize[1]);
        int numCars = Integer.parseInt(reader.readLine());

        // Create The Starting Configuration
        String[][] board = new String[rows][cols];
        Car[] cars = new Car[numCars];

        // "*" Will Be Empty
        for (String[] s : board)
        {
            Arrays.fill(s, EMPTY_SPACE);
        }

        for (int i = 0; i < numCars; i++)
        {

            String[] carData = reader.readLine().split(" ");
            String car = carData[0];

            // Create Car From X1->X2, Y1->Y2
            // For The Ease of Calculations, (x1, x2) and (y1, y2) are in ascending order
            int x1 = Math.min(Integer.parseInt(carData[1]), Integer.parseInt(carData[3]));
            int y1 = Math.min(Integer.parseInt(carData[2]), Integer.parseInt(carData[4]));

            int x2 = Math.max(Integer.parseInt(carData[1]), Integer.parseInt(carData[3]));
            int y2 = Math.max(Integer.parseInt(carData[2]), Integer.parseInt(carData[4]));

            for (int x = x1; x <= x2; x++)
            {
                for (int y = y1; y <= y2; y++)
                {
                    board[x][y] = car;
                }
            }

            cars[i] = new Car(car, x1, x2, y1, y2);
        }

        return new JamNode(null, board, Arrays.asList(cars));
    }

    // I know it's garbage to just have a static and nonstatic, but it makes things work
    public static JamNode readFileToGame(String filename) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(filename));

        // Read In Board Size & Number of Cars
        var boardSize = reader.readLine().split(" ");
        int rows = Integer.parseInt(boardSize[0]);
        int cols = Integer.parseInt(boardSize[1]);
        int numCars = Integer.parseInt(reader.readLine());

        // Create The Starting Configuration
        String[][] board = new String[rows][cols];
        Car[] cars = new Car[numCars];

        // "*" Will Be Empty
        for (String[] s : board)
        {
            Arrays.fill(s, EMPTY_SPACE);
        }

        for (int i = 0; i < numCars; i++)
        {

            String[] carData = reader.readLine().split(" ");
            String car = carData[0];

            // Create Car From X1->X2, Y1->Y2
            // For The Ease of Calculations, (x1, x2) and (y1, y2) are in ascending order
            int x1 = Math.min(Integer.parseInt(carData[1]), Integer.parseInt(carData[3]));
            int y1 = Math.min(Integer.parseInt(carData[2]), Integer.parseInt(carData[4]));

            int x2 = Math.max(Integer.parseInt(carData[1]), Integer.parseInt(carData[3]));
            int y2 = Math.max(Integer.parseInt(carData[2]), Integer.parseInt(carData[4]));

            for (int x = x1; x <= x2; x++)
            {
                for (int y = y1; y <= y2; y++)
                {
                    board[x][y] = car;
                }
            }

            cars[i] = new Car(car, x1, x2, y1, y2);
        }

        return new JamNode(null, board, Arrays.asList(cars));
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1)
        {
            System.out.println("Usage: java Jam filename");
        }

        JamNode root = readFileToGameStatic(args);
        System.out.println(root);

        Solver solver = new Solver(root);
        solver.BFS();


        // Printing
        System.out.println(solver.successful);
        System.out.println("Total configs: " + solver.total);
        System.out.println("Unique configs: " + solver.unique);

    }


}