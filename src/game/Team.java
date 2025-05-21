package game;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import board.Space;
import pieces.Bishop;
import pieces.Knight;
import pieces.Piece;

public class Team {

    private final TeamColor color;

    private Space enPassantOpportunity;

    private Team opponent;

    private final Set<Piece> pieces;

    private Team(TeamColor teamColor) {
        color = teamColor;
        pieces = new HashSet<>();
    }

    public static Players initPlayers() {
        Team black = new Team(TeamColor.BLACK);
        Team white = new Team(TeamColor.WHITE);

        black.opponent = white;
        white.opponent = black;

        return new Players(black, white);
    }

    public void add(Piece piece) {
        pieces.add(piece);
    }

    public TeamColor getColor() {
        return color;
    }

    public Space getEnPassantOpportunity() {
        return enPassantOpportunity;
    }

    public Team getOpponent() {
        return opponent;
    }

    public boolean hasInsufficientMaterial() {
        return pieces.size() == 1 ||
               (pieces.size() == 2 && stream().anyMatch(piece -> piece instanceof Knight || piece instanceof Bishop));
    }

    public void remove(Piece piece) {
        pieces.remove(piece);
    }

    public void setEnPassantOpportunity(Space targetSpace) {
        enPassantOpportunity = targetSpace;
    }

    public Stream<Piece> stream() {
        return pieces.stream();
    }

    public static class Players {

        private final Team black;

        private Team currentTeam;

        private final Team white;

        private Players(Team black, Team white) {
            this.black = black;
            currentTeam = black; // start as black so the initial team toggle flips to white
            this.white = white;
        }

        public Team getBlack() {
            return black;
        }

        public Team getCurrentTeam() {
            return currentTeam;
        }

        public Team getWhite() {
            return white;
        }

        public boolean isDraw() {
            return black.hasInsufficientMaterial() && white.hasInsufficientMaterial();
        }

        public void toggleCurrentTeam() {
            currentTeam.setEnPassantOpportunity(null);
            currentTeam = currentTeam.getOpponent();
        }

    }
}
