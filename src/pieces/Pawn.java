package pieces;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import board.Space;
import game.Team;
import game.TeamColor;

public class Pawn extends Piece {

    private final int direction;

    private final int startRow;

    public Pawn(TeamColor team, int startRow) {
        super(team);
        direction = team == TeamColor.WHITE ? 1 : -1;
        this.startRow = startRow;
    }

    private static void addSpace(Space checkSpace, Function<Space, Boolean> checkFn, Consumer<Space> addFn) {
        if (checkFn.apply(checkSpace)) {
            addFn.accept(checkSpace);
        }
    }

    private static boolean isValidNormalSpace(Space checkSpace) {
        return checkSpace != null && checkSpace.getOccupant() == null;
    }

    @Override
    public Set<Space> getMovementDomain() {
        Set<Space> domain = new HashSet<>();
        Space checkSpace = curSpace.calculateMove(-1, direction);
        addSpace(checkSpace, this::isValidAttackSpace, domain::add);
        checkSpace = curSpace.calculateMove(1, direction);
        addSpace(checkSpace, this::isValidAttackSpace, domain::add);

        checkSpace = curSpace.calculateMove(0, direction);
        addSpace(checkSpace, Pawn::isValidNormalSpace, domain::add);
        if (curSpace.getRow() == startRow) {
            checkSpace = curSpace.calculateMove(0, direction * 2);
            addSpace(checkSpace, Pawn::isValidNormalSpace, domain::add);
        }
        return domain;
    }

    @Override
    public char getToken() {
        return 'P';
    }

    private boolean isValidAttackSpace(Space checkSpace) {
        return checkSpace != null && checkSpace.getOccupant() != null && checkSpace.getOccupant().getTeam() != getTeam();
    }
}
