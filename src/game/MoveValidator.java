package game;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

import board.Board;
import board.Space;
import pieces.King;
import pieces.Knight;
import pieces.Piece;

class MoveValidator {

    static boolean isValidInput(String move) {
        return Pattern.matches("^([BKNQR][A-H1-8]?)?[A-H][1-8]$", move);
    }

    static boolean isValid(Piece movingPiece, Space target, Board board) {
        return movingPiece.getMovementDomain().contains(target)
                && !generatePath(movingPiece, target).isEmpty()
                && notInCheck(movingPiece, target, board);
    }

    static Set<Space> generatePath(Piece movingPiece, Space target) {
        Set<Space> path = new HashSet<>();
        if (!(movingPiece instanceof Knight)) {
            Function<Space, Space> movement = getMovementFn(movingPiece.getCurSpace(), target);
            for (Space curSpace = movement.apply(movingPiece.getCurSpace()); !target.equals(curSpace); curSpace = movement.apply(curSpace)) {
                if (curSpace.getOccupant() != null) {
                    return Set.of();
                }
                path.add(curSpace);
            }
        }
        path.add(movingPiece.getCurSpace());
        path.add(target);
        return path;
    }

    private static Function<Space, Space> getMovementFn(Space start, Space target) {
        int colDif = target.getCol() - start.getCol();
        int colIncrement = colDif == 0 ? 0 : colDif / Math.abs(colDif);

        int rowDif = target.getRow() - start.getRow();
        int rowIncrement = rowDif == 0 ? 0 : rowDif / Math.abs(rowDif);

        return (space) -> space.calculateMove(colIncrement, rowIncrement);
    }

    private static boolean notInCheck(Piece movingPiece, Space target, Board board) {
        Space spaceToCheck;
        if (movingPiece instanceof King) {
            spaceToCheck = target;
        } else {
            Piece king = board.getPieces(movingPiece.getTeam()).stream().filter(King.class::isInstance).findAny().orElseThrow(IllegalStateException::new);
            spaceToCheck = king.getCurSpace();
        }

        Piece curOccupant = target.getOccupant();
        Space startSpace = movingPiece.getCurSpace();
        target.setOccupant(movingPiece);
        movingPiece.setCurSpace(target);
        startSpace.setOccupant(null);

        Team oppositeTeam = Team.getOpposite(movingPiece.getTeam());
        Set<Piece> oppositePieces = board.getPieces(oppositeTeam);

        boolean notInCheck = oppositePieces.stream().filter(piece -> !piece.equals(curOccupant)).noneMatch(piece -> isValid(piece, spaceToCheck, board));

        target.setOccupant(curOccupant);
        startSpace.setOccupant(movingPiece);
        movingPiece.setCurSpace(startSpace);
        return notInCheck;
    }

}
