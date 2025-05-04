package input;

import java.util.regex.Matcher;

public class InputParser {

    private final Matcher input;

    private final InputDto dto;

    public InputParser(Matcher input) {
        this.input = input;
        dto = new InputDto();
    }

    private static Character extractChar(String str) {
        if (str != null) {
            return str.charAt(0);
        }
        return null;
    }

    private static Integer extractInt(String integer) {
        if (integer != null) {
            return Integer.parseInt(integer);
        }
        return null;
    }

    public InputDto parse() {
        if (input.group("castle") != null) {
            parseCastleInput();
        } else {
            parseNormalInput();
        }
        return dto;
    }

    private void parseCastleInput() {
        dto.castling = true;
        if (input.group("castle").endsWith("-O-O")) {
            dto.castlingRookCol = 'A';
        } else {
            dto.castlingRookCol = 'H';
        }
    }

    private void parseNormalInput() {
        dto.movingPieceToken = extractChar(input.group("targetToken"));
        dto.originCol = extractChar(input.group("originCol"));
        dto.originRow = extractInt(input.group("originRow"));

        String destination = input.group("destination");
        dto.destinationCol = destination.charAt(0);
        dto.destinationRow = Character.digit(destination.charAt(1), 10);

        String pawnPromotion = input.group("pawnPromotion");
        if (pawnPromotion != null) {
            dto.pawnPromotionToken = pawnPromotion.charAt(1);
        }
    }
}
