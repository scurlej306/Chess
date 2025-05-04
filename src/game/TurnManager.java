package game;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import board.Board;
import board.Space;
import input.InputDto;
import input.InputParser;
import input.InputReader;
import move.Move;
import move.MoveFactory;
import move.MoveValidator;
import pieces.King;
import pieces.Piece;

class TurnManager {

    private final Board board;

    private TeamColor currentTeam;

    TurnManager(Board board) {
        this.board = board;
        currentTeam = TeamColor.BLACK; // Start as black, so the first toggle starts the game as white
    }

    static boolean isValidInput(String move) {
        return Pattern.matches("^([BKNQR][A-H1-8]?)?[A-H][1-8]|O(-O){1,2}$", move);
    }

    TeamColor getCurrentTeam() {
        return currentTeam;
    }

    GameState processTurn() throws IOException {
        Matcher matcher = new InputReader(currentTeam).read();
        InputDto input = new InputParser(matcher).parse();
        return processMove(input);
    }

    void toggleCurrentTeam() {
        currentTeam = TeamColor.getOpposite(currentTeam);
    }

    private GameState determineState() {
        if (isCheckmate()) {
            return GameState.CHECKMATE;
        }
        if (isStalemate()) {
            return GameState.STALEMATE;
        }
        if (isDraw()) {
            return GameState.DRAW;
        }
        return GameState.ONGOING;
    }

    private boolean isCheckmate() {
        TeamColor opponentTeam = TeamColor.getOpposite(currentTeam);
        Piece opponentKing = opponentTeam.stream().filter(King.class::isInstance).findAny().orElseThrow(IllegalStateException::new);

        /* Determine if any piece sees the opponent king. */
        List<Set<Space>> checkVectors = currentTeam.stream().filter(piece -> MoveValidator.isValid(piece, opponentKing.getCurSpace(), board))
                .map(piece -> MoveValidator.generatePath(piece, opponentKing.getCurSpace())).toList();

        /* No active checks, so no checkmate. */
        if (checkVectors.isEmpty()) {
            return false;
        }

        /* If only 1 active check and an opponent piece can take or block, no checkmate. */
        if (checkVectors.size() == 1 && checkVectors.getFirst().stream().anyMatch(space -> opponentTeam.stream().anyMatch(piece -> MoveValidator.isValid(piece, space, board)))) {
            return false;
        }

        /* Lastly, if the king can move to any adjacent space without also being in check, no checkmate. */
        return opponentKing.getMovementDomain().stream().noneMatch(space -> MoveValidator.isValid(opponentKing, space, board));
    }

    private boolean isDraw() {
        return TeamColor.BLACK.hasInsufficientMaterial() && TeamColor.WHITE.hasInsufficientMaterial();
    }

    private boolean isStalemate() {
        return TeamColor.getOpposite(currentTeam).stream().noneMatch(piece -> piece.getMovementDomain().stream().anyMatch(space -> MoveValidator.isValid(piece, space, board)));
    }

    private GameState processMove(InputDto input) throws IOException {
        Move move;
        try {
            move = new MoveFactory(input, board, currentTeam).constructMove();
        } catch (IllegalArgumentException e) {
            System.out.printf("Move '%s' had the following error: %s. Try again.\n", input, e.getMessage());
            return processTurn();
        }
        move.doMove(board);
        return determineState();
    }
}
