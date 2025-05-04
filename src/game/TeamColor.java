package game;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import pieces.Bishop;
import pieces.Knight;
import pieces.Piece;

public enum TeamColor {
    BLACK,
    WHITE;

    private final Set<Piece> pieces;

    TeamColor() {
        pieces = new HashSet<>();
    }
    public static TeamColor getOpposite(TeamColor curTeam) {
        return curTeam == BLACK ? WHITE : BLACK;
    }

    public void add(Piece piece) {
        pieces.add(piece);
    }

    public void remove(Piece piece) {
        pieces.remove(piece);
    }

    public Stream<Piece> stream() {
        return pieces.stream();
    }

    public boolean hasInsufficientMaterial() {
        return pieces.size() == 1 ||
               (pieces.size() == 2 && stream().anyMatch(piece -> piece instanceof Knight || piece instanceof Bishop));
    }
}
