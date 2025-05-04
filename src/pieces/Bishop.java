package pieces;

import java.util.HashSet;
import java.util.Set;

import board.Space;
import game.Team;
import game.TeamColor;

public class Bishop extends Piece {
    public Bishop(TeamColor team) {
        super(team);
    }

    @Override
    public Set<Space> getMovementDomain() {
        Set<Space> spaces = new HashSet<>();
        for (int i = 1; i < 8; i++) {
            spaces.add(curSpace.calculateMove(i, i));
            spaces.add(curSpace.calculateMove(i, -i));
            spaces.add(curSpace.calculateMove(-i, i));
            spaces.add(curSpace.calculateMove(-i, -i));
        }
        spaces.removeIf(space -> space == null || (space.getOccupant() != null && space.getOccupant().getTeam() == getTeam()));
        return spaces;
    }

    @Override
    public char getToken() {
        return 'B';
    }
}
