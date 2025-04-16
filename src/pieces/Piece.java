package pieces;

import java.util.Set;

import board.Space;
import game.Team;

public abstract class Piece {
    private final Team team;
    protected Space curSpace;

    protected Piece(Team team) {
        this.team = team;
    }

    public abstract Set<Space> getMovementDomain();

    public Space getCurSpace() {
        return curSpace;
    }

    public Team getTeam() {
        return team;
    }

    public abstract char getToken();

    public void setCurSpace(Space nextSpace) {
        curSpace = nextSpace;
    }
}
