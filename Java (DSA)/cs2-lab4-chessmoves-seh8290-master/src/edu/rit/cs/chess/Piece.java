package edu.rit.cs.chess;

/*
 * This tiny skeleton of code is put here only to get the code we
 * give you to compile.
 * You basically must implement this class from scratch.
 */

import edu.rit.cs.util.ActionResult;
import edu.rit.cs.util.Coordinates;

/**
 * @author Sam Harris
 */
public abstract class Piece {

    public String name; // Might Change to an Int Identifier Instead
    public Game game; // I really don't like the idea of having each piece have a version of the board
    private Coordinates cords; // I was about to write a point class, but found Coordinates - good job cs dept

    public Piece(Coordinates cords, String name, Game game) {
        this.cords = cords;
        this.name = name;
        this.game = game;
    }

    public abstract ActionResult isLegalMove(Coordinates move);

    public ActionResult makeMove(Coordinates move) {
        if (isLegalMove(move).ok)
        {
            movePieceTo(move);
            return ActionResult.OK;
        }
        return new ActionResult("Move Failed");
    }

    public void movePieceTo(Coordinates move) {
        game.board[move.row()][move.column()] = this;
        game.board[cords.row()][cords.column()] = null;
        this.setPos(move);
    }

    // Here There Be Getters and Setters:
    public Game getGame() {
        return game;
    }

    public int row() {
        return this.cords.row();
    }

    public int col() {
        return this.cords.column();
    }

    public void setPos(Coordinates c) {
        this.cords = c;
    }

    public void setPos(int row, int col) {
        this.cords = new Coordinates(row, col);
    }

    public Coordinates getCords() {
        return cords;
    }

    public String toString() {
        return name;
    }

}

