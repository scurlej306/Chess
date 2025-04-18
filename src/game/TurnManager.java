package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

import board.Board;
import board.Space;
import pieces.King;
import pieces.Piece;

class TurnManager {

    private final Board board;

    private Team currentTeam;

    TurnManager(Board board) {
        this.board = board;
        currentTeam = Team.BLACK; // Start as black, so the first toggle starts the game as white
    }

    Team getCurrentTeam() {
        return currentTeam;
    }

    GameState processTurn() throws IOException {
        String input;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("Enter a move for " + currentTeam);
            input = reader.readLine().toUpperCase().trim();
        } while (!MoveValidator.isValidInput(input));
        return processMove(input);
    }

    void toggleCurrentTeam() {
        currentTeam = Team.getOpposite(currentTeam);
    }

    private GameState processMove(String input) throws IOException {
        Move move;
        try {
            move = new Move.Builder(input, board, currentTeam).build();
        } catch (IllegalArgumentException e) {
            System.out.printf("Move '%s' had the following error: %s. Try again.\n", input, e.getMessage());
            return processTurn();
        }
        move.doMove(board);
        return determineState();
    }

    private GameState determineState() {
        return isNotCheckmate() ? GameState.ONGOING : GameState.CHECKMATE;
    }

    private boolean isNotCheckmate() {
        Set<Piece> currentTeamPieces = board.getPieces(currentTeam);
        Set<Piece> opponentPieces = board.getPieces(Team.getOpposite(currentTeam));
        Piece opponentKing = opponentPieces.stream().filter(King.class::isInstance).findAny().orElseThrow(IllegalStateException::new);

        /* Determine if any piece sees the opponent king. */
        List<Set<Space>> checkVectors = currentTeamPieces.stream().filter(piece -> MoveValidator.isValid(piece, opponentKing.getCurSpace(), board))
                .map(piece -> MoveValidator.generatePath(piece, opponentKing.getCurSpace(), board)).toList();

        /* No active checks, so no checkmate. */
        if (checkVectors.isEmpty()) {
            return true;
        }

        /* If only 1 active check and an opponent piece can take or block, no checkmate. */
        if (checkVectors.size() == 1 && checkVectors.getFirst().stream().anyMatch(space -> opponentPieces.stream().anyMatch(piece -> MoveValidator.isValid(piece, space, board)))) {
            return true;
        }

        /* Lastly, if the king can move to any adjacent space without also being in check, no checkmate. */
        return opponentKing.getMovementDomain().stream().anyMatch(space -> MoveValidator.isValid(opponentKing, space, board));
    }

}
