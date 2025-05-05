package move;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import board.Board;
import board.Space;
import game.Team;
import input.InputDto;
import pieces.King;
import pieces.Piece;
import pieces.Rook;

public record MoveFactory(InputDto input, Board board, Team currentTeam) {

    public Move constructMove() {
        if (input.castling) {
            return constructCastleMove();
        }
        return constructNormalMove();
    }

    private Predicate<Piece> canReachTargetSpace(Space targetSpace) {
        return piece -> MoveValidator.isValid(piece, targetSpace, board);
    }

    private CastleMove constructCastleMove() {
        int kingColChange;
        int rookColChange;
        if (input.castlingRookCol == 'H') {
            kingColChange = 2;
            rookColChange = -2;
        } else {
            kingColChange = -2;
            rookColChange = 3;
        }
        King king =
                (King) currentTeam.stream().filter(King.class::isInstance).findFirst().orElseThrow(IllegalStateException::new);
        if (king.hasAlreadyMoved()) {
            throw new IllegalArgumentException("The King has already moved. Castling is not allowed");
        }
        Space kingTargetSpace = king.getCurSpace().calculateMove(kingColChange, 0);
        Set<Space> kingMoves = MoveValidator.generatePath(king, kingTargetSpace);
        if (kingMoves.isEmpty()) {
            throw new IllegalArgumentException("There cannot be any pieces between the king and its target space");
        }
        if (currentTeam.getOpponent().stream().anyMatch(piece -> kingMoves.stream()
                .anyMatch(space -> MoveValidator.isValid(piece, space, board)))) {
            throw new IllegalArgumentException("The king cannot move out of, through, or into check");
        }

        Rook rook =
                (Rook) currentTeam.stream().filter(Rook.class::isInstance).filter(
                                piece -> piece.getCurSpace().getCol() == input.castlingRookCol).findFirst()
                        .orElseThrow(IllegalStateException::new);
        if (rook.hasAlreadyMoved()) {
            throw new IllegalArgumentException("The desired rook has already moved. Castling with this rook is not allowed");
        }
        Space targetRookSpace = rook.getCurSpace().calculateMove(rookColChange, 0);
        if (!MoveValidator.isValid(rook, targetRookSpace, board)) {
            throw new IllegalArgumentException("The rook cannot legally move to its target space");
        }
        Move rookMove = new Move(rook, rook.getCurSpace().calculateMove(rookColChange, 0));
        return new CastleMove(king, kingTargetSpace, rookMove);
    }

    private Move constructNormalMove() {
        Space targetSpace = board.getSpace(input.destinationCol, input.destinationRow);
        Set<Piece> validPieces = currentTeam.stream().filter(
                        matchesToken().and(matchesDisambiguator()).and(canReachTargetSpace(targetSpace)))
                .collect(Collectors.toSet());

        if (validPieces.isEmpty()) {
            throw new IllegalArgumentException("No piece of the provided type can reach the target space");
        }
        if (validPieces.size() > 1) {
            throw new IllegalArgumentException("Ambiguous move - multiple pieces of the provided" +
                                               " type can reach the target space");
        }

        Piece targetPiece = validPieces.iterator().next();
        return new Move(targetPiece, targetSpace);
    }

    private Predicate<Piece> matchesDisambiguator() {
        if (input.originCol != null && input.originRow != null) {
            Space origin = board.getSpace(input.originCol, input.originRow);
            return piece -> piece.getCurSpace().equals(origin);
        }
        if (input.originCol != null) {
            return piece -> piece.getCurSpace().getCol() == input.originCol;
        }
        if (input.originRow != null) {
            return piece -> piece.getCurSpace().getRow() == input.originRow;
        }
        return _ -> true;
    }

    private Predicate<Piece> matchesToken() {
        char token = input.movingPieceToken != null ? input.movingPieceToken : 'P';
        return piece -> piece.getToken() == token;
    }
}
