package pieces;

import java.util.Set;

import board.Space;
import game.Team;

public abstract class Piece {

    protected Space curSpace;

    private final Team team;

    protected Piece(Team team) {
        this.team = team;
    }

    public Space getCurSpace() {
        return curSpace;
    }

    public abstract Set<Space> getMovementDomain();

    public Team getTeam() {
        return team;
    }

    public abstract char getToken();

    public void setCurSpace(Space nextSpace) {
        curSpace = nextSpace;
    }
}
