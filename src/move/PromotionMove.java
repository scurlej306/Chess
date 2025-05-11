package move;

import game.Team;
import pieces.*;

public class PromotionMove extends Move {

    private final Character promotionToken;

    PromotionMove(Move pawnMove, Character promotionToken) {
        super(pawnMove.targetPiece, pawnMove.targetSpace);
        this.promotionToken = promotionToken;
    }

    @Override
    public void doMove() {
        super.doMove();
        Team curTeam = targetPiece.getTeam();
        Piece promotion = switch (promotionToken) {
            case 'B' -> new Bishop(curTeam);
            case 'N' -> new Knight(curTeam);
            case 'Q' -> new Queen(curTeam);
            case 'R' -> {
                Rook rook = new Rook(curTeam);
                rook.invalidateCastling();
                yield rook;
            }
            default -> throw new IllegalStateException("A move was invoked with an illegal pawn promotion.");
        };

        curTeam.remove(targetPiece);
        curTeam.add(promotion);
        targetSpace.setOccupant(promotion);
        promotion.setCurSpace(targetSpace);
    }

}
