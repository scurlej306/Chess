package pieces;

import java.util.HashSet;
import java.util.Set;

import board.Space;
import game.Team;

public class Rook extends Piece {
    public Rook(Team team) {
        super(team);
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
        results.remove(null);
        return results;
    }

    @Override
    public char getToken() {
        return 'R';
    }
}
