package move;

import board.Board;
import board.Space;
import pieces.Castles;
import pieces.Piece;

public class Move {

    private final Piece targetPiece;
    private final Space targetSpace;

    Move(Piece targetPiece, Space targetSpace) {
        this.targetPiece = targetPiece;
        this.targetSpace = targetSpace;
    }

    public void doMove(Board board) {
        Piece prevOccupant = targetSpace.getOccupant();
        if (prevOccupant != null) {
            board.getPieces(prevOccupant.getTeam()).remove(prevOccupant);
        }
        if (targetPiece instanceof Castles castles) {
            castles.invalidateCastling();
        }
        targetPiece.getCurSpace().setOccupant(null);
        targetPiece.setCurSpace(targetSpace);
        targetSpace.setOccupant(targetPiece);
    }
}
