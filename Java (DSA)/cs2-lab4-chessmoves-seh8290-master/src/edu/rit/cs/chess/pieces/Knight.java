package edu.rit.cs.chess.pieces;

import edu.rit.cs.chess.Game;
import edu.rit.cs.chess.Piece;
import edu.rit.cs.util.ActionResult;
import edu.rit.cs.util.Coordinates;

public class Knight extends Piece {


    public Knight(Coordinates cords, String name, Game game) {
        super(cords, name, game);
    }

    @Override
    public ActionResult isLegalMove(Coordinates move) {

        if ((Math.abs(row() - move.row()) == 2 && Math.abs(col() - move.column()) == 1) ||
            (Math.abs(row() - move.row()) == 1 && Math.abs(col() - move.column()) == 2))
        {
            return ActionResult.OK;
        } else
        {
            return new ActionResult("Invalid Knight Move");
        }
    }
}
