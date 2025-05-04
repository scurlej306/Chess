package game;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

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

    private final Team.Players players;

    TurnManager(Board board, Team.Players players) {
        this.board = board;
        this.players = players;
    }

    GameState processTurn() throws IOException {
        Matcher matcher = new InputReader(players.getCurrentTeam().getColor()).read();
        InputDto input = new InputParser(matcher).parse();
        return processMove(input);
    }

    private GameState determineState() {
        if (isCheckmate()) {
            return GameState.CHECKMATE;
        }
        if (isStalemate()) {
            return GameState.STALEMATE;
        }
        if (players.isDraw()) {
            return GameState.DRAW;
        }
        return GameState.ONGOING;
    }

    private boolean isCheckmate() {
        Team opponentTeam = players.getCurrentTeam().getOpponent();
        Piece opponentKing = opponentTeam.stream().filter(King.class::isInstance).findAny().orElseThrow(IllegalStateException::new);

        /* Determine if any piece sees the opponent king. */
        List<Set<Space>> checkVectors = players.getCurrentTeam().stream().filter(piece -> MoveValidator.isValid(piece, opponentKing.getCurSpace(), board))
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

    private boolean isStalemate() {
        return players.getCurrentTeam().getOpponent().stream().noneMatch(piece -> piece.getMovementDomain().stream().anyMatch(space -> MoveValidator.isValid(piece, space, board)));
    }

    private GameState processMove(InputDto input) throws IOException {
        Move move;
        try {
            move = new MoveFactory(input, board, players.getCurrentTeam()).constructMove();
        } catch (IllegalArgumentException e) {
            System.out.printf("Move '%s' had the following error: %s. Try again.\n", input, e.getMessage());
            return processTurn();
        }
        move.doMove(board);
        return determineState();
    }
}
