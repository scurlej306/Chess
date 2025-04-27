package move;

import board.Board;
import board.Space;
import game.Team;
import pieces.King;
import pieces.Piece;
import pieces.Rook;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record MoveFactory(String input, Board board, Team currentTeam) {

    public Move constructMove() {
        if (input.startsWith("O")) {
            return constructCastleMove();
        }
        return constructNormalMove();
    }

    private CastleMove constructCastleMove() {
        int kingColChange;
        int rookColChange;
        char rookStartCol;
        if ("O-O".equals(input)) {
            kingColChange = 2;
            rookColChange = -2;
            rookStartCol = 'H';
        } else {
            kingColChange = -2;
            rookColChange = 3;
            rookStartCol = 'A';
        }
        King king =
                (King) board.getPieces(currentTeam).stream().filter(King.class::isInstance).findFirst().orElseThrow(IllegalStateException::new);
        if (king.hasAlreadyMoved()) {
            throw new IllegalArgumentException("The King has already moved. Castling is not allowed.");
        }
        Space kingTargetSpace = king.getCurSpace().calculateMove(kingColChange, 0);

        Rook rook =
                (Rook) board.getPieces(currentTeam).stream().filter(Rook.class::isInstance).filter(piece -> piece.getCurSpace().getCol() == rookStartCol).findFirst().orElseThrow(IllegalStateException::new);
        if (rook.hasAlreadyMoved()) {
            throw new IllegalArgumentException("The desired rook has already moved. Castling with this rook is not " + "allowed.");
        }
        Move rookMove = new Move(rook, rook.getCurSpace().calculateMove(rookColChange, 0));
        return new CastleMove(king, kingTargetSpace, rookMove);
    }

    private Move constructNormalMove() {
        Space targetSpace = getTargetSpace();
        char token = input.length() > 2 ? input.charAt(0) : 'P';
        Set<Piece> validPieces =
                board.getPieces(currentTeam).stream().filter(piece -> piece.getToken() == token && MoveValidator.isValid(piece, targetSpace, board)).collect(Collectors.toSet());
        if (validPieces.isEmpty()) {
            throw new IllegalArgumentException("No piece of the provided type can reach the target space");
        }
        Piece targetPiece;
        if (validPieces.size() > 1) {
            Predicate<Piece> pieceMatcher = getPiecePredicate();
            targetPiece =
                    validPieces.stream().filter(pieceMatcher).findFirst().orElseThrow(() -> new IllegalArgumentException("Ambiguous move - multiple pieces of the provided" + " type can reach the target space"));
        } else {
            targetPiece = validPieces.iterator().next();
        }

        return new Move(targetPiece, targetSpace);
    }

    private Predicate<Piece> getPiecePredicate() {
        if (input.length() != 4) {
            /* No determinate provided, so make the predicate return no matches. */
            return piece -> false;
        }
        char determinate = input.charAt(1);
        if (Character.isDigit(determinate)) {
            int row = Character.digit(determinate, 10);
            return piece -> piece.getCurSpace().getRow() == row;
        }
        return piece -> piece.getCurSpace().getCol() == determinate;
    }

    private Space getTargetSpace() {
        int targetRow = Character.digit(input.charAt(input.length() - 1), 10);
        char targetCol = input.charAt(input.length() - 2);
        return board.getSpace(targetCol, targetRow);
    }
}
