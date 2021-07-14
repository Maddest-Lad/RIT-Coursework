package edu.rit.cs.chess.pieces;

import edu.rit.cs.chess.Game;
import edu.rit.cs.chess.Piece;
import edu.rit.cs.util.ActionResult;
import edu.rit.cs.util.Coordinates;

public class Queen extends Piece {

    public Queen(Coordinates cords, String name, Game game) {
        super(cords, name, game);
    }

    @Override
    public ActionResult isLegalMove(Coordinates move) {

        if (((Math.abs(this.row() - move.row()) == Math.abs(this.col() - move.column())
          || ((this.col() == move.column() && this.row() != move.row())
          ||  (this.col() != move.column() && this.row() == move.row()))))
          && game.isClearPath(this.getCords(), move))
        {
            return ActionResult.OK;
        } else
        {
            return new ActionResult("Not True Diagonal/Lateral Movement");
        }
    }

}
