package tentsandtrees.backtracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The full representation of a configuration in the TentsAndTrees puzzle.
 * It can read an initial configuration from a file, and supports the
 * Configuration methods necessary for the Backtracker solver.
 *
 * @author RIT CS
 * @author Sam Harris
 */
public class TentConfig implements Configuration {

    // Input Constants, thanks I hate it
    public final static String EMPTY = ".";
    public final static String GRASS = "-";
    public final static String TENT = "^";
    public final static String TREE = "%";

    // Vertical / Horizontal Divider
    public final static String HORI_DIVIDE = "-";
    public final static String VERT_DIVIDE = "|";
    // Convert String to Internal Format
    private final static Map<String, TentItems> toInternal = Map.of(
            EMPTY, TentItems.Empty,
            GRASS, TentItems.Grass,
            TREE, TentItems.Tree,
            TENT, TentItems.Tent
    );
    // Convert Internal Format Back to Output
    private final static Map<TentItems, String> toOutput = Map.of(
            TentItems.Empty, EMPTY,
            TentItems.Grass, GRASS,
            TentItems.Tree, TREE,
            TentItems.Tent, TENT
    );
    // Instance Variables
    private final TentItems[][] board;
    private int[] col;
    private int[] row;

    /**
     * Construct the initial configuration from an input file whose contents
     * are, for example:
     * <p>
     * 3        # square dimension of field
     * 2 0 1    # row looking values, top to bottom
     * 2 0 1    # column looking values, left to right
     * . % .    # row 1, .=empty, %=tree
     * % . .    # row 2
     * . % .    # row 3
     *
     * @param filename the name of the file to read from
     * @throws IOException if the file is not found or there are errors reading
     */
    public TentConfig(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));

        // Initialize the Board and Side Trackers
        int size = Integer.parseInt(in.readLine());
        this.board = new TentItems[size][size];
        this.col = new int[size];
        this.row = new int[size];

        // Get The Row & Col, Lol streams are great
        this.col = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        this.row = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // Then Loop Through in Size Times To Read in The rows
        for (int i = 0; i < size; i++)
        {

            var boardVals = Arrays.asList(in.readLine().split(" "));

            for (int j = 0; j < size; j++)
            {
                board[i][j] = toInternal.get(boardVals.get(j));
            }
        }

        in.close();  // <3 Jimmy


        // Do Some Input Sanitization
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[0].length; j++)
            {
                if(!getNeighborsAround(i, j).containsKey(TentItems.Tree)) {
                    board[i][j] = TentItems.Grass;
                }
            }
        }

    }

    /**
     * Copy constructor.  Takes a config, other, and makes a full "deep" copy
     * of its instance data.
     *
     * @param other the config to copy
     */
    private TentConfig(TentConfig other) {

        // Probably Sketchy, But Should Work
        this.board = new TentItems[other.board.length][other.board[0].length];

        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[0].length; j++)
            {
                this.board[i][j] = other.board[i][j];
            }
        }

        this.col = other.col.clone();
        this.row = other.row.clone();
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        ArrayList<Configuration> successors = new ArrayList<>();

        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[0].length; j++)
            {

                if(board[i][j] == TentItems.Empty) {

                    if(getNeighborsPlus(i, j).containsKey(TentItems.Tree)) {
                        board[i][j] = TentItems.Tent;
                        successors.add(new TentConfig(this));

                    } else {
                        board[i][j] = TentItems.Grass;
                        successors.add(new TentConfig(this));
                    }
                }
            }
        }
        return successors;
    }

    @Override
    public boolean isValid() {

        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[0].length; j++)
            {
                if ((getTentInCol(j) > col[j] && getTentInCol(j) <= col[j]) || (getTentInRow(i) > row[i] && getTentInRow(i) <= row[i]))
                {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isGoal() {
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[0].length; j++)
            {
                if (getTentInCol(j) != col[j] || getTentInRow(i) != row[i])
                {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public String toString() {

        // God This Whole Method is EVIL

        StringBuilder out = new StringBuilder();

        out.append(" ");
        String repeat = HORI_DIVIDE.repeat(Math.max(0, (board.length * 2) - 1));
        out.append(repeat);
        out.append("\n");

        // Watch Everything fail
        for (int i = 0; i < board.length; i++)
        {
            out.append(VERT_DIVIDE);
            for (int j = 0; j < board[0].length; j++)
            {
                if (j < board.length - 1)
                {
                    out.append(toOutput.get(board[i][j])).append(" ");
                } else
                {
                    out.append(toOutput.get(board[i][j]));
                }

            }
            out.append(VERT_DIVIDE).append(col[i]).append("\n");
        }

        out.append(" ");
        out.append(repeat);
        out.append("\n");

        out.append(" ");
        for (int i = 0; i < row.length; i++)
        {

            if (i < row.length - 1)
            {
                out.append(row[i]).append(" ");
            } else
            {
                out.append(row[i]);
            }
        }

        out.append("\n");

        return out.toString();                 // replace
    }

    //HELPER METHODS

    // Gets A Count of What Neighbors Are Around Tile in a Plus Sign Excluding The Middle
    Map<TentItems, Integer> getNeighborsPlus(int x, int y) {
        Map<TentItems, Integer> neighbors = new HashMap<>();

        try {
            neighbors.merge(board[x-1][y], 1, Integer::sum);
        } catch (Exception ignored) {}

        try {
            neighbors.merge(board[x+1][y], 1, Integer::sum);
        } catch (Exception ignored) {}

        try {
            neighbors.merge(board[x][y-1], 1, Integer::sum);
        } catch (Exception ignored) {}

        try {
            neighbors.merge(board[x][y+1], 1, Integer::sum);
        } catch (Exception ignored) {}

        return neighbors;
    }

    // Gets A Count of What Neighbors Are Around Tile in a 3x3 Grid Excluding The Middle
    Map<TentItems, Integer> getNeighborsAround(int x, int y) {
        Map<TentItems, Integer> neighbors = new HashMap<>();

        for (int i = x - 1; i <= x + 1; i++)
        {
            for (int j = y - 1; j <= y + 1; j++)
            {

                // In Range
                if (i >= 0 && i < board.length && j >= 0 && j < board[0].length)
                {
                    neighbors.merge(board[i][j], 1, Integer::sum);
                }
            }
        }

        // This Thing is Very Dumb So
        neighbors.merge(board[x][y], -1, Integer::sum);

        return neighbors;
    }

    // Returns The Number of Tents Around (x,y)
    // 3x3 For True, + For False
    int getTentsAroundTile(int x, int y, boolean plusOrAround) {

        // Tents Adjacent To Each Other
        var neighbors = (plusOrAround) ? getNeighborsAround(x, y) : getNeighborsPlus(x, y);
        if (neighbors.containsKey(TentItems.Tent))
        {
            return neighbors.get(TentItems.Tent);
        }
        return 0;
    }

    int getTentInCol(int y) {
        int count = 0;

        for (int i = 0; i < board[0].length; i++)
        {
            if (board[y][i] == TentItems.Tent)
            {
                count++;
            }
        }

        return count;
    }

    int getTentInRow(int x) {
        int count = 0;

        for (int i = 0; i < board[0].length; i++)
        {
            if (board[i][x] == TentItems.Tent)
            {
                count++;
            }
        }

        return count;
    }

    // Avoid Char/String Where Possible
    enum TentItems {
        Empty,
        Grass,
        Tree,
        Tent,
    }

}
