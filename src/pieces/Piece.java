package pieces;

import java.util.Set;

import board.Space;
import game.TeamColor;

public abstract class Piece {
    private final TeamColor team;
    protected Space curSpace;

    protected Piece(TeamColor team) {
        this.team = team;
    }

    public abstract Set<Space> getMovementDomain();

    public Space getCurSpace() {
        return curSpace;
    }

    public TeamColor getTeam() {
        return team;
    }

    public abstract char getToken();

    public void setCurSpace(Space nextSpace) {
        curSpace = nextSpace;
    }
}
