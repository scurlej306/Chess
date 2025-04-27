package move;

import board.Board;
import board.Space;
import pieces.Piece;

class CastleMove extends Move {

    private final Move rookMove;

    CastleMove(Piece targetPiece, Space targetSpace, Move rookMove) {
        super(targetPiece, targetSpace);
        this.rookMove = rookMove;
    }

    @Override
    public void doMove(Board board) {
        super.doMove(board);
        rookMove.doMove(board);
    }
}
