package board;

import java.util.Objects;

import pieces.Piece;

public class Space {
    private final char col;
    private final int row;
    private final int hash;
    private Piece occupant;

    public Space(char col, int row) {
        this.col = col;
        this.row = row;
        this.hash = Objects.hash(col, row);
        this.occupant = null;
    }

    public Space calculateMove(int colChange, int rowChange) {
        char newCol = (char) (col + colChange);
        if (newCol < 'A' || newCol > 'H') {
            return null;
        }
        int newRow = row + rowChange;
        if (newRow < 1 || newRow > 8) {
            return null;
        }
        return new Space(newCol, newRow);
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
