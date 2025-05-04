package pieces;

import java.util.HashSet;
import java.util.Set;

import board.Space;
import game.Team;
import game.TeamColor;

public class Knight extends Piece {

    public Knight(TeamColor team) {
        super(team);
    }

    @Override
    public Set<Space> getMovementDomain() {
        Set<Space> spaces = new HashSet<>();
        spaces.add(curSpace.calculateMove(1, 2));
        spaces.add(curSpace.calculateMove(-1, 2));
        spaces.add(curSpace.calculateMove(1, -2));
        spaces.add(curSpace.calculateMove(-1, -2));
        spaces.add(curSpace.calculateMove(2, 1));
        spaces.add(curSpace.calculateMove(-2, 1));
        spaces.add(curSpace.calculateMove(2, -1));
        spaces.add(curSpace.calculateMove(-2, -1));
        spaces.removeIf(space -> space == null || (space.getOccupant() != null && space.getOccupant().getTeam() == getTeam()));
        return spaces;
    }

    @Override
    public char getToken() {
        return 'N';
    }
}
