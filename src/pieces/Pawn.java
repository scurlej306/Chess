package pieces;

import java.util.Set;

import board.Space;
import game.Team;

public class Pawn extends Piece {

    private final int direction;

    private final int startRow;

    public Pawn(Team team, int startRow) {
        super(team);
        direction = team == Team.WHITE ? 1 : -1;
        this.startRow = startRow;
    }

    @Override
    public Set<Space> getMovementDomain() {
        int nextRow = curSpace.getRow() + direction;
        if (curSpace.getRow() == startRow) {
            return Set.of(new Space(curSpace.getCol(), nextRow), new Space(curSpace.getCol(), nextRow + direction));
        }
        return Set.of(new Space(curSpace.getCol(), nextRow));
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public char getToken() {
        return 'P';
    }
}
