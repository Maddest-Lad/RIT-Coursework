package edu.rit.cs.chess.pieces;

import edu.rit.cs.chess.Game;
import edu.rit.cs.chess.Piece;
import edu.rit.cs.util.ActionResult;
import edu.rit.cs.util.Coordinates;

public class King extends Piece {

    public King(Coordinates cords, String name, Game game) {
        super(cords, name, game);
    }

    @Override
    public ActionResult isLegalMove(Coordinates move) {

        // Only Move 1 Tile
        if (Math.abs(this.col() - move.column()) <= 1 && Math.abs(this.row() - move.row()) <= 1 && game.isClearPath(this.getCords(), move))
        {
            return ActionResult.OK;
        } else
        {
            return new ActionResult("Trying to Move More Than 1 Tile");
        }
    }
}
