package pieces;

import java.util.HashSet;
import java.util.Set;

import board.Space;
import game.Team;
import game.TeamColor;

public class Queen extends Piece {
    public Queen(TeamColor team) {
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
            spaces.add(curSpace.calculateMove(0, i));
            spaces.add(curSpace.calculateMove(0, -i));
            spaces.add(curSpace.calculateMove(i, 0));
            spaces.add(curSpace.calculateMove(-i, 0));
        }
        spaces.removeIf(space -> space == null || (space.getOccupant() != null && space.getOccupant().getTeam() == getTeam()));
        return spaces;
    }

    @Override
    public char getToken() {
        return 'Q';
    }
}
