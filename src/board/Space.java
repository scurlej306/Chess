package board;

import java.util.Objects;

import pieces.Piece;

public class Space {
    private final char col;
    private final int row;
    private final int hash;
    private final Board board;
    private Piece occupant;

    public Space(char col, int row, Board board) {
        this.col = col;
        this.row = row;
        this.hash = Objects.hash(col, row);
        this.board = board;
        this.occupant = null;
    }

    public Space calculateMove(int colChange, int rowChange) {
        char newCol = (char) (col + colChange);
        int newRow = row + rowChange;
        return board.getSpace(newCol, newRow);
    }

    public char getCol() {
        return col;
    }

    public Piece getOccupant() {
        return occupant;
    }

    public int getRow() {
        return row;
    }

    public void setOccupant(Piece newOccupant) {
        occupant = newOccupant;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Space other)) {
            return false;
        }
        return this.col == other.col && this.row == other.row;
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
