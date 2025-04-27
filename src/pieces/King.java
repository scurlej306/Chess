package pieces;

import java.util.HashSet;
import java.util.Set;

import board.Space;
import game.Team;

public class King extends Piece implements Castles {

    boolean alreadyMoved;
    public King(Team team) {
        super(team);
        this.alreadyMoved = false;
    }

    @Override
    public Set<Space> getMovementDomain() {
        Set<Space> spaces = new HashSet<>();
        spaces.add(curSpace.calculateMove(-1, -1));
        spaces.add(curSpace.calculateMove(-1, 0));
        spaces.add(curSpace.calculateMove(-1, 1));
        spaces.add(curSpace.calculateMove(0, -1));
        spaces.add(curSpace.calculateMove(0, 1));
        spaces.add(curSpace.calculateMove(1, -1));
        spaces.add(curSpace.calculateMove(1, 0));
        spaces.add(curSpace.calculateMove(1, 1));
        spaces.removeIf(space -> space == null || (space.getOccupant() != null && space.getOccupant().getTeam() == getTeam()));
        return spaces;
    }

    @Override
    public char getToken() {
        return 'K';
    }

    @Override
    public boolean hasAlreadyMoved() {
        return alreadyMoved;
    }

    @Override
    public void invalidateCastling() {
        alreadyMoved = true;
    }
}
