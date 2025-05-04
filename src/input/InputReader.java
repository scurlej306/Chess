package input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import game.TeamColor;

public class InputReader {

    private static final Pattern INPUT_PATTERN = Pattern.compile("^(?<targetToken>[BKNQR])?(?<originCol>[A-H])?" +
                                           "(?<originRow>[1-8])?(?<destination>[A-H][1-8])(?<pawnPromotion>=[BNQR])?|" +
                                                                 "(?<castle>O(-O){1,2})$");

    private final TeamColor currentTeam;
    private final BufferedReader reader;

    public InputReader(TeamColor currentTeam) {
        this.currentTeam = currentTeam;
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public Matcher read() throws IOException {
        System.out.println("Enter a move for " + currentTeam);
        String input = reader.readLine().toUpperCase().trim();
        Matcher matcher = INPUT_PATTERN.matcher(input);
        if (!matcher.matches()) {
            System.out.println("The input must match the pattern:");
            System.out.println("    <piece type (optional)><origin column (optional)>" +
                               "<origin row (optional)><destination column><destination row>< '=' and the type of " +
                               "piece for pawn promotion (optional)>");
            System.out.println("or be either 'O-O' or 'O-O-O'.");
            return read();
        }
        return matcher;
    }

}
