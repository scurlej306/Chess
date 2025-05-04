package board;

import java.util.HashMap;
import java.util.Map;

import game.TeamColor;
import pieces.*;

public class Board {
    private final Map<String, Space> board;

    public Board() {
        board = new HashMap<>();
        initSpaces();
        initPieces(TeamColor.WHITE, 1, 1);
        initPieces(TeamColor.BLACK, 8, -1);
    }

    private static String getKey(char col, int row) {
        return String.format("%c%d", col, row);
    }

    public Space getSpace(char col, int row) {
        return board.get(getKey(col, row));
    }

    private void initPieces(TeamColor team, int backRow, int direction) {
        setPiece(getSpace('A', backRow), team, new Rook(team));
        setPiece(getSpace('B', backRow), team, new Knight(team));
        setPiece(getSpace('C', backRow), team, new Bishop(team));
        setPiece(getSpace('D', backRow), team, new Queen(team));
        setPiece(getSpace('E', backRow), team, new King(team));
        setPiece(getSpace('F', backRow), team, new Bishop(team));
        setPiece(getSpace('G', backRow), team, new Knight(team));
        setPiece(getSpace('H', backRow), team, new Rook(team));

        int pawnRow = backRow + direction;
        for (char col = 'A'; col < 'I'; col++) {
            setPiece(getSpace(col, pawnRow), team, new Pawn(team, pawnRow));
        }
    }

    private void initSpaces() {
        for (char curChar = 'A'; curChar < 'I'; curChar++) {
            for (int curInt = 1; curInt < 9; curInt++) {
                board.put(getKey(curChar, curInt), new Space(curChar, curInt, this));
            }
        }
    }

    private void setPiece(Space space, TeamColor team, Piece piece) {
        space.setOccupant(piece);
        piece.setCurSpace(space);
        team.add(piece);
    }
}
