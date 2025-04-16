package game;

public enum Team {
    BLACK,
    WHITE;

    public static Team getOpposite(Team curTeam) {
        return curTeam == BLACK ? WHITE : BLACK;
    }
}
