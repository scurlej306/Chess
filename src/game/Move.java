package game;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import board.Board;
import board.Space;
import pieces.Piece;

record Move(Space targetSpace, Piece targetPiece) {

    void doMove(Board board) {
        Piece prevOccupant = targetSpace.getOccupant();
        if (prevOccupant != null) {
            board.getPieces(prevOccupant.getTeam()).remove(prevOccupant);
        }

        targetPiece.getCurSpace().setOccupant(null);
        targetPiece.setCurSpace(targetSpace);
        targetSpace.setOccupant(targetPiece);
    }

    record Builder(String input, Board board, Team currentTeam) {

        Move build() {
            Space targetSpace = getTargetSpace();
            char token = input.length() > 2 ? input.charAt(0) : 'P';
            Set<Piece> validPieces = board.getPieces(currentTeam).stream()
                    .filter(piece -> piece.getToken() == token && MoveValidator.isValid(piece, targetSpace, board))
                    .collect(Collectors.toSet());
            if (validPieces.isEmpty()) {
                throw new IllegalArgumentException("No piece of the provided type can reach the target space");
            }
            Piece targetPiece;
            if (validPieces.size() > 1) {
                Predicate<Piece> pieceMatcher = getPiecePredicate();
                targetPiece = validPieces.stream().filter(pieceMatcher).findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Ambiguous move - multiple pieces of the provided type can reach the target space"));
            } else {
                targetPiece = validPieces.iterator().next();
            }

            return new Move(targetSpace, targetPiece);
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
}
