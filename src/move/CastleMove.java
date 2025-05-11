package move;

import board.Space;
import pieces.King;

class CastleMove extends Move {

    private final Move rookMove;

    CastleMove(King king, Space targetSpace, Move rookMove) {
        super(king, targetSpace);
        this.rookMove = rookMove;
    }

    @Override
    public void doMove() {
        super.doMove();
        rookMove.doMove();
    }
}
