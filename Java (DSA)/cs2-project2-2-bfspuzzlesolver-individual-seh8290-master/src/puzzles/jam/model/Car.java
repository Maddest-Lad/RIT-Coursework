package puzzles.jam.model;

import puzzles.jam.solver.Jam;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Car {

    // Class Vars
    static Map<Move, Move> oppositeMove = Map.of(
            Move.UP, Move.DOWN,
            Move.DOWN, Move.UP,
            Move.LEFT, Move.RIGHT,
            Move.RIGHT, Move.LEFT);

    // Instance Vars
    public String ID;
    public boolean isHorizontal; // False == Vertical
    public int x1, x2, y1, y2;

    public Car(String ID, int x1, int x2, int y1, int y2) {
        this.ID = ID;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;

        this.isHorizontal = Math.abs(y1 - y2) != 0;
    }

    public Car(Car car) {
        this.ID = car.ID;
        this.x1 = car.x1;
        this.x2 = car.x2;
        this.y1 = car.y1;
        this.y2 = car.y2;
        this.isHorizontal = car.isHorizontal;

    }

    public List<Move> canMove(String[][] board) {

        List<Move> moves = new LinkedList<>();

        if (isHorizontal)
        {
            // Move Left
            if (y1 > 0 && board[x1][y1 - 1].equals(Jam.EMPTY_SPACE))
            {
                moves.add(Move.LEFT);
            }

            // Move Right
            if (y2 < board.length - 1 && board[x1][y2 + 1].equals(Jam.EMPTY_SPACE))
            {
                moves.add(Move.RIGHT);

            }

        } else
        {
            if (x1 > 0 && board[x1 - 1][y1].equals(Jam.EMPTY_SPACE))
            {
                moves.add(Move.UP);
            }

            if (x2 < board[0].length - 1 && board[x2 + 1][y1].equals(Jam.EMPTY_SPACE))
            {
                moves.add(Move.DOWN);
            }

        }
        return moves.size() != 0 ? moves : List.of(Move.NONE);
    }

    public void move(Move move, String[][] board) {

        System.out.println(move);

        if (move == Move.LEFT)
        {
            board[x1][y1 - 1] = ID;
            board[x1][y2] = Jam.EMPTY_SPACE;
            y1--;
            y2--;
        }

        if (move == Move.RIGHT)
        {
            board[x1][y2 + 1] = ID;
            board[x1][y1] = Jam.EMPTY_SPACE;
            y1++;
            y2++;
        }

        if (move == Move.UP)
        {
            board[x1 - 1][y1] = ID;
            board[x2][y1] = Jam.EMPTY_SPACE;
            x1--;
            x2--;
        }

        if (move == Move.DOWN)
        {
            board[x2 + 1][y1] = ID;
            board[x1][y1] = Jam.EMPTY_SPACE;
            x1++;
            x2++;
        }
    }

    void reset(Move move) {
        if (move == Move.LEFT)
        {
            y1++;
            y2++;
        }

        if (move == Move.RIGHT)
        {
            y1--;
            y2--;
        }

        if (move == Move.UP)
        {
            x1++;
            x2++;
        }

        if (move == Move.DOWN)
        {
            x1--;
            x2--;
        }
    }

    void printPosition() {
        System.out.println(ID + " X1:" + x1 + " X2:" + x2 + " Y1:" + y1 + " Y2:" + y2);
    }

}
