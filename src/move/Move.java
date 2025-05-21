package move;

import board.Space;
import pieces.Castles;
import pieces.Pawn;
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
        } else if (targetPiece instanceof Pawn pawn) {
            int curRow = pawn.getCurSpace().getRow();
            int targetRow = targetSpace.getRow();
            int movementAmt = Math.abs(curRow - targetRow);
            if (movementAmt == 2) {
                Space skippedSpace = targetSpace.calculateMove(0, -pawn.getDirection());
                pawn.getTeam().getOpponent().setEnPassantOpportunity(skippedSpace);
            } else if (targetSpace.equals(pawn.getTeam().getEnPassantOpportunity())) {
                Piece capturedPiece = targetSpace.calculateMove(0, -pawn.getDirection()).getOccupant();
                capturedPiece.getCurSpace().setOccupant(null);
                capturedPiece.getTeam().remove(capturedPiece);
            }
        }
        targetPiece.getCurSpace().setOccupant(null);
        targetPiece.setCurSpace(targetSpace);
        targetSpace.setOccupant(targetPiece);
    }
}
