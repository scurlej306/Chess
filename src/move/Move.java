package move;

import board.Space;
import pieces.Castles;
import pieces.Piece;

public class Move {

    protected final Piece targetPiece;

    protected final Space targetSpace;

    Move(Piece targetPiece, Space targetSpace) {
        this.targetPiece = targetPiece;
        this.targetSpace = targetSpace;
    }

    public void doMove() {
        Piece prevOccupant = targetSpace.getOccupant();
        if (prevOccupant != null) {
            prevOccupant.getTeam().remove(prevOccupant);
        }
        if (targetPiece instanceof Castles castles) {
            castles.invalidateCastling();
        }
        targetPiece.getCurSpace().setOccupant(null);
        targetPiece.setCurSpace(targetSpace);
        targetSpace.setOccupant(targetPiece);
    }
}
