package game;

import java.util.Set;
import java.util.stream.Collectors;

import board.Board;
import board.Space;
import pieces.Piece;

record Move(Space startSpace, Space targetSpace, Piece targetPiece) {

    void doMove(Board board) {
        Piece prevOccupant = targetSpace.getOccupant();
        if (prevOccupant != null) {
            board.getPieces(prevOccupant.getTeam()).remove(prevOccupant);
        }

        targetSpace.setOccupant(targetPiece);
        targetPiece.setCurSpace(targetSpace);
        startSpace.setOccupant(null);
    }

    record Builder(String input, Board board, Team currentTeam) {

        Move build() {
            Space targetSpace = getTargetSpace(input);
            Space startSpace;
            Piece targetPiece;
            if (input.length() == 5) {
                startSpace = getTargetSpace(input.substring(0, 3));
                targetPiece = startSpace.getOccupant();
                if (targetPiece.getToken() != input.charAt(0)) {
                    throw new IllegalArgumentException("Provided piece type does not match piece at start space");
                }
                if (!MoveValidator.isValid(targetPiece, targetSpace, board)) {
                    throw new IllegalArgumentException("The piece on the provided start space cannot reach the target space");
                }
            } else {
                char token = input.length() == 3 ? input.charAt(0) : 'P';
                Set<Piece> validPieces = board.getPieces(currentTeam).stream()
                        .filter(piece -> piece.getToken() == token && MoveValidator.isValid(piece, targetSpace, board))
                        .collect(Collectors.toSet());
                if (validPieces.isEmpty()) {
                    throw new IllegalArgumentException("No piece of the provided type can reach the target space");
                }
                if (validPieces.size() > 1) {
                    throw new IllegalArgumentException("Ambiguous move - multiple pieces of the provided type can reach the target space");
                }
                targetPiece = validPieces.iterator().next();
                startSpace = targetPiece.getCurSpace();
            }

            return new Move(startSpace, targetSpace, targetPiece);
        }

        private Space getTargetSpace(String input) {
            int targetRow = Character.digit(input.charAt(input.length() - 1), 10);
            char targetCol = input.charAt(input.length() - 2);
            return board.getSpace(targetCol, targetRow);
        }

    }
}
