package pieces;

import java.util.HashSet;
import java.util.Set;

import board.Space;
import game.Team;
import game.TeamColor;

public class Rook extends Piece implements Castles {
    private boolean alreadyMoved;

    public Rook(TeamColor team) {
        super(team);
        alreadyMoved = false;
    }

    @Override
    public Set<Space> getMovementDomain() {
        Set<Space> results = new HashSet<>();
        for (int i = 1; i < 8; i++) {
            results.add(curSpace.calculateMove(0, i));
            results.add(curSpace.calculateMove(0, -i));
            results.add(curSpace.calculateMove(i, 0));
            results.add(curSpace.calculateMove(-i, 0));
        }
        results.removeIf(space -> space == null || (space.getOccupant() != null && space.getOccupant().getTeam() == getTeam()));
        return results;
    }

    @Override
    public char getToken() {
        return 'R';
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
