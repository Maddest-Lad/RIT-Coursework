package edu.rit.cs.chess.pieces;

import edu.rit.cs.chess.Game;
import edu.rit.cs.chess.Piece;
import edu.rit.cs.util.ActionResult;
import edu.rit.cs.util.Coordinates;

public class Pawn extends Piece {

    public Pawn(Coordinates cords, String name, Game game) {
        super(cords, name, game);
    }

    @Override
    public ActionResult isLegalMove(Coordinates move) {

        // Pawns Should Only Move Forward 1 Tile (Row Wise)
        if (this.row() - move.row() == 1 && this.col() == move.column())
        {
           return ActionResult.OK;
        } else
        {
            return new ActionResult("Pawns Can't Move More Than 1 Row Up");
        }
    }
}
