package edu.rit.cs.chess.pieces;

import edu.rit.cs.chess.Game;
import edu.rit.cs.chess.Piece;
import edu.rit.cs.util.ActionResult;
import edu.rit.cs.util.Coordinates;

public class Rook extends Piece {


    public Rook(Coordinates cords, String name, Game game) {
        super(cords, name, game);
    }

    @Override
    public ActionResult isLegalMove(Coordinates move) {
        // No Diagonal Movement
        if ((this.col() == move.column() && this.row() != move.row()) || (this.col() != move.column() && this.row() == move.row()))
        {
            if (game.isClearPath(this.getCords(), move))
            {
                return ActionResult.OK;
            } else
            {
                return new ActionResult("Path is Blocked");
            }
        } else
        {
            return new ActionResult("No Diagonal Allowed");
        }
    }
}
