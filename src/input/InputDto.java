package input;

import java.util.Objects;

public class InputDto {

    public boolean castling = false;

    public Character castlingRookCol;

    public Character destinationCol;

    public Integer destinationRow;

    public Character movingPieceToken;

    public Character originCol;

    public Integer originRow;

    public Character pawnPromotionToken;

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof InputDto that)) {
            return false;
        }
        if (this == that) {
            return true;
        }
        return this.castling == that.castling && this.castlingRookCol == that.castlingRookCol &&
               this.destinationCol == that.destinationCol && Objects.equals(this.destinationRow, that.destinationRow) &&
               this.movingPieceToken == that.movingPieceToken && this.originCol == that.originCol &&
               Objects.equals(this.originRow, that.originRow) && this.pawnPromotionToken == that.pawnPromotionToken;
    }

    @Override
    public int hashCode() {
        return Objects.hash(castling, castlingRookCol, destinationCol, destinationRow, movingPieceToken, originCol,
                originRow, pawnPromotionToken);
    }

    @Override
    public String toString() {
        return String.join("\n",
                "InputDto: {",
                "\tcastling: " + castling,
                "\tcastlingRookCol: " + castlingRookCol,
                "\tdestinationCol: " + destinationCol,
                "\tdestinationRow: " + destinationRow,
                "\tmovingPieceToken: " + movingPieceToken,
                "\toriginCol: " + originCol,
                "\toriginRow: " + originRow,
                "\tpawnPromotionToken: " + pawnPromotionToken,
                "}"
        );
    }
}
