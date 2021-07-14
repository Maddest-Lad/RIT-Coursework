package puzzles.jam.model;

import puzzles.common.solver.Node;
import puzzles.jam.solver.Jam;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class JamNode extends Node {

    public List<Car> cars;
    public int rows;
    public int cols;
    private String[][] board;

    public JamNode(Node parent, String[][] board, List<Car> cars) {
        super(parent);
        this.board = board;
        this.cars = cars;
        this.rows = board[0].length;
        this.cols = board.length;
    }

    public JamNode(JamNode copy, String[][] board) {
        super(copy);
        this.board = board;
        this.rows = board[0].length;
        this.cols = board.length;

        // More Deep Copy Fun
        this.cars = new LinkedList<>();

        for (Car car : copy.cars)
        {
            this.cars.add(new Car(car));
        }
    }

    @Override
    public boolean isGoal() {
        // Scan Last Column For X
        for (int row = 0; row < rows - 2; row++)
        {
            if (board[row][cols - 1].equals("X"))
            {
                return true;
            }
        }

        return false;
    }

    // For PTUI Hint
    public String possibleMoves() {

        StringBuilder sb = new StringBuilder();

        for (Car car : cars)
        {
            List<Move> currentMoves = new LinkedList<>(car.canMove(this.board));

            if (!currentMoves.contains(Move.NONE))
            {
                sb.append(car.ID).append(" can make the following moves: ");

                for (Move move : currentMoves)
                {
                    sb.append(move).append(" ");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public List<Node> generateChildren() {
        List<Node> children = new LinkedList<>();

        for (Car car : cars)
        {
            for (Move move : car.canMove(this.board))
            {
                if (move != Move.NONE)
                {
                    var tempBoard = deepCopy(board);
                    //System.out.println("Moving Car " + car.ID + " : " + move + "\n");
                    car.move(move, tempBoard);
                    children.add(new JamNode(this, tempBoard));
                    car.reset(move);
                }
            }
        }

        return children;
    }

    private String[][] deepCopy(String[][] board) {
        String[][] newBoard = new String[board.length][];
        for (int i = 0; i < board.length; i++)
        {
            newBoard[i] = Arrays.copyOf(board[i], board[i].length);
        }
        return newBoard;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof JamNode)
        {
            for (int i = 0; i < board.length; i++)
            {
                for (int j = 0; j < board[0].length; j++)
                {
                    if (!board[i][j].equals(((JamNode) obj).board[i][j]))
                    {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }


    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String[] row : board)
        {
            for (String value : row)
            {
                sb.append(value).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String[][] getBoard() {
        return board;
    }

    public MoveStatus Move(int x1, int y1, int x2, int y2) {
        if (board[y2][x2].equals(Jam.EMPTY_SPACE))
        {
            // Find Car
            for (Car c : cars)
            {
                if (c.ID.equals(board[y1][x1]))
                {
                    Move move = getDirection(x1, y1, x2, y2);
                    if ((c.isHorizontal && (move == Move.LEFT || move == Move.RIGHT)) || !c.isHorizontal && (move == Move.UP || move == Move.DOWN))
                    {
                        if (move == Move.NONE)
                        {
                            return MoveStatus.FAILED;
                        }
                        c.move(move, board);
                        return MoveStatus.SUCCESS;
                    }
                }
            }
        }

        return MoveStatus.FAILED;
    }

    public Move getDirection(int x1, int y1, int x2, int y2) {

        if (x1 == x2 && y1 < y2)
        {
            return Move.DOWN;
        }

        if (x1 == x2 && y1 > y2)
        {
            return Move.UP;
        }

        if (y1 == y2 && x1 < x2)
        {
            return Move.RIGHT;
        }

        if (y1 == y2 && x2 < x1)
        {
            return Move.LEFT;
        }

        return Move.NONE;
    }

    public Node nextStep() {
        Random r = new Random();
        var children = generateChildren();
        return children.get(r.nextInt(children.size()));
    }


}
