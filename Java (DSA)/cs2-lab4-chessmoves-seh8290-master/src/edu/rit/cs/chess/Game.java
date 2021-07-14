package edu.rit.cs.chess;

import edu.rit.cs.chess.pieces.*;
import edu.rit.cs.util.ActionResult;
import edu.rit.cs.util.Coordinates;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is the class representing the entire solitaire chess game.
 * An instance of this class contains the board and the pieces currently
 * on the board.
 *
 * @author RIT CS
 * @author Sam Harris
 */
public class Game {

    /**
     * A line in the configuration file that starts with this character
     * is ignored.
     */
    public static final char COMMENT = '#';

    /**
     * Board dimensions
     */
    private final int numRows, numCols;

    /**
     * Indicates whether Game is in a good state after initialization.
     * If ok is false, the object's use should not continue.
     */
    public final boolean ok;

    /**
     * A 2D array of board cells
     */
    public Piece[][] board;

    /**
     * Initialize the game -- both the board and the pieces.
     *
     * @param setupFileName Line 1 contains dimensions. The rest are pieces.
     * @throws FileNotFoundException if the file could not be processed
     * @throws NumberFormatException if non-integer data is provided in the file
     *                               where integers were required
     */
    public Game(String setupFileName) throws FileNotFoundException {
        Scanner setupFile = new Scanner(new File(setupFileName));

        // Open the file and read the first line -- board dimensions
        String firstLine;
        do
        {
            firstLine = setupFile.nextLine();
        } while (firstLine.charAt(0) == '#');
        String[] dims = firstLine.split("\\s+");
        if (dims.length != 2)
        {
            System.out.println(
                    "Improper first line of config file: " + firstLine
            );
            this.ok = false;
            // The following lines exist only to silence compiler warnings.
            this.numRows = -1;
            this.numCols = -1;
        } else
        {
            // First line (board dimensions) seems good.
            // One of the next two lines could have a parse-int error,
            // but we don't explicitly handle that. Program will crash.
            this.numRows = Integer.parseInt(dims[0]);
            this.numCols = Integer.parseInt(dims[1]);

            // Create a matrix of null pieces.
            this.board = new Piece[this.numRows][this.numCols];

            // Read the rest of the file.
            // Each line has information on a new piece.
            while (setupFile.hasNextLine())
            {
                final String line = setupFile.nextLine();

                // Skip blank lines and comment lines.
                if (line.length() == 0 ||
                        line.charAt(0) == COMMENT) continue; // Go to next line.

                String[] pieces = line.split("\\s+");
                if (pieces.length != 3)
                {
                    System.out.println("Improper config line: " + line);
                    continue; // Go to next line.
                }

                Coordinates pos = new Coordinates(pieces[1], pieces[2]);
                if (pos.row() < 0 || pos.row() >= numRows)
                {
                    System.out.println("Illegal row: " + line);
                    continue; // Go to next line.
                }
                if (pos.column() < 0 || pos.column() >= numCols)
                {
                    System.out.println("Illegal column: " + line);
                    continue; // Go to next line.
                }

                // Using the first letter of the piece's name, determine
                // what kind of piece it is, and create an instance of
                // the correct subclass of Piece.

                Piece piece = createPiece(pieces[0], pos);
                if (piece == null) continue; // Piece name was illegal.

                // Put the new piece on the board.
                placePiece(pos, piece);
            }
            setupFile.close();
            this.ok = true;
        }
    }

    /**
     * Put the given piece at the given position.
     * This will erase any piece that is already there.
     *
     * @param pos   the given position
     * @param piece the given piece
     */
    private void placePiece(Coordinates pos, Piece piece) {
        board[pos.row()][pos.column()] = piece;
        piece.setPos(pos);
    }

    /**
     * Is any piece at this location?
     *
     * @param pos the location on the board
     * @return true iff there is a piece at the location
     */
    private boolean occupied(Coordinates pos) {
        return board[pos.row()][pos.column()] != null;
    }

    /**
     * Get a piece on the board.
     *
     * @param dest board cell coordinates
     * @return the piece at that cell, or null if no piece is at that cell
     */
    private Piece getPiece(Coordinates dest) {
        return board[dest.row()][dest.column()];
    }

    /**
     * Remove a piece from the board, e.g., due to capture.
     *
     * @param pos board cell coordinates
     */
    private void clearCell(Coordinates pos) {
        board[pos.row()][pos.column()] = null;
    }

    /**
     * Create a new piece and put it on the board
     *
     * @param pieceName the name's first letter determines the piece type.
     *                  Case of that letter can be lower or upper.
     *                  R-rook; N-knight; B-bishop; Q-queen; K-king; P-pawn
     * @param pos       coordinates of board cell in which to place the piece
     * @return an instance of a subclass of Piece based on the arguments,
     * or null, if the pieceName was illegal
     */
    private Piece createPiece(String pieceName, Coordinates pos) {

        char identifier = pieceName.toLowerCase().toCharArray()[0];
        Piece result = null;

        if (identifier == 'k') { result = new King(pos, pieceName, this);  }
        if (identifier == 'p') { result = new Pawn(pos, pieceName, this);  }
        if (identifier == 'r') { result = new Rook(pos, pieceName, this);  }
        if (identifier == 'q') { result = new Queen(pos, pieceName, this); }
        if (identifier == 'n') { result = new Knight(pos, pieceName, this);}
        if (identifier == 'b') { result = new Bishop(pos, pieceName, this);}

        board[pos.row()][pos.column()] = result;
        return result;
    }

    /**
     * Attempt to move the piece at one location on the board to a
     * new location. If there is a piece at the new location,
     * the original piece captures it and it is removed from the board.
     * The only error checks done here are to make sure there is a piece
     * at the start coordinates, and if both coordinate pairs are in-bounds.
     * The piece itself determines if the move would be legal.
     *
     * @param start the current location of the piece
     * @param end   the intended new location of the piece
     * @return An ActionResult indicating success or the reason for failure
     */
    public ActionResult makeMove(Coordinates start, Coordinates end) {
        // Check if row or column is out of bounds.
        if (0 > start.row() || start.row() >= numRows ||
                0 > start.column() || start.column() >= numCols)
        {
            return new ActionResult("No such position: " + start);
        }
        if (0 > end.row() || end.row() >= numRows ||
                0 > end.column() || end.column() >= numCols)
        {
            return new ActionResult("No such destination: " + end);
        }

        if (getPiece(start) == null)
        {
            return new ActionResult("No Piece here");
        }

        return getPiece(start).makeMove(end);
    }

    /**
     * Can a piece travel in a straight line from the start to the destination
     * without encountering other pieces along the way?
     *
     * @param start the starting position
     * @param end   the destination position
     * @return true iff there are no pieces in between the start and the
     * end (exclusive)
     * @rit.pre both start and end are in bounds.
     * @rit.pre the line from the start to the destination is either
     * horizontal, vertical, or a 45° diagonal.
     */
    public boolean isClearPath(Coordinates start, Coordinates end) {
        Coordinates delta = start.delta(end);
        final int dRow = delta.row();
        final int dCol = delta.column();
        Coordinates incr = new Coordinates(
                Integer.signum(dRow),
                Integer.signum(dCol)
        );
        for (Coordinates pos = start.plus(incr);
             !pos.equals(end);
             pos = pos.plus(incr))
        {
            assert pos.row() >= 0 && pos.row() < numRows &&
                    pos.column() >= 0 && pos.column() < numCols :
                    "Preconditions of isClearPath violated!";
            if (occupied(pos)) return false;
        }
        return true;

    }

    /**
     * Display the entire game board, with coordinates, on the console.
     *
     * @rit.pre Piece names should be no more than two characters long.
     */
    public void showBoard() {
        System.out.println();
        // Column coordinate row
        System.out.print("    ");
        for (int c = 0; c < this.board[0].length; ++c)
        {
            System.out.printf("%2d ", c);
        }
        System.out.println();

        for (int r = 0; r < this.board.length; ++r)
        {
            System.out.printf("%2d  ", r);
            for (Piece piece : this.board[r])
            {
                System.out.print(
                        (piece == null) ? "__ "
                                : String.format("%-3s", piece.toString())
                );
            }
            System.out.println();
        }
        System.out.println();
    }
}
