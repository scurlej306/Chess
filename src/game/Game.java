package game;

import java.io.IOException;

import board.Board;
import board.Space;
import pieces.Piece;

public class Game {

    private final Board board;

    private final Team.Players players;

    private final TurnManager turnManager;

    public Game() {
        players = Team.initPlayers();
        board = new Board(players);
        turnManager = new TurnManager(board, players);
    }

    public void start() {
        try {
            GameState state = GameState.ONGOING;
            while (state == GameState.ONGOING) {
                drawBoard();
                players.toggleCurrentTeam();
                state = turnManager.processTurn();
            }
            drawBoard();
            reportFinalState(state);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.out.println("Game aborted.");
        }
    }

    private void drawBoard() {
        System.out.println("    A    B    C    D    E    F    G    H    ");
        System.out.println("  |----|----|----|----|----|----|----|----|  ");
        for (int row = 8; row > 0; row--) {
            System.out.printf("%d | ", row);
            for (char col = 'A'; col < 'I'; col++) {
                Space space = board.getSpace(col, row);
                Piece occupant = space.getOccupant();
                String token = occupant != null ? String.format("%c%c", occupant.getTeam().getColor().name().toLowerCase().charAt(0), occupant.getToken()) : "  ";
                System.out.printf("%s | ", token);
            }
            System.out.println(row);
            System.out.println("  |----|----|----|----|----|----|----|----|  ");
        }
        System.out.println("    A    B    C    D    E    F    G    H    ");
    }

    private void reportFinalState(GameState state) {
        switch (state) {
            case DRAW -> System.out.println("Draw. Game over.");
            case CHECKMATE -> System.out.printf("Checkmate! %s wins!", players.getCurrentTeam().getColor());
            case STALEMATE -> System.out.println("Stalemate. Game over.");
        }
    }

}
