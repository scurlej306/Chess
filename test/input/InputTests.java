package input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;
import java.util.regex.Matcher;

import game.TeamColor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InputTests {

    private static InputStream origIn;

    private static PrintStream origOut;

    private static ByteArrayOutputStream testOut;

    @BeforeAll
    static void before() {
        origOut = System.out;
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        origIn = System.in;
    }

    @AfterAll
    static void cleanup() {
        System.setOut(origOut);
        System.setIn(origIn);
    }

    private static void testCase(String input, InputDto expected) throws IOException {
        System.setIn(new ByteArrayInputStream((input + "\n").getBytes()));

        InputReader reader = new InputReader(TeamColor.WHITE);
        Matcher matcher = reader.read();

        InputDto output = new InputParser(matcher).parse();
        assertEquals(expected, output);
        assertEquals(expected.hashCode(), output.hashCode());
        assertEquals(expected.toString(), output.toString());
    }

    @Test
    void testInvalidInput() throws IOException {
        System.setIn(new ByteArrayInputStream("bad input\ne4".getBytes()));

        InputReader reader = new InputReader(TeamColor.WHITE);
        reader.read();

        assertTrue(testOut.toString().contains("The input must match the pattern"));
    }

    @Test
    void testKingsideCastle() throws IOException {
        InputDto expected = new InputDto();
        expected.castling = true;
        expected.castlingRookCol = 'H';
        testCase("O-O", expected);
    }

    @Test
    void testQueensideCastle() throws IOException {
        InputDto expected = new InputDto();
        expected.castling = true;
        expected.castlingRookCol = 'A';
        testCase("O-O-O", expected);
    }

    @Test
    void test_Bf8_Input() throws IOException {
        InputDto expected = new InputDto();
        expected.movingPieceToken = 'B';
        expected.destinationRow = 8;
        expected.destinationCol = 'F';
        testCase("bf8", expected);
    }

    @Test
    void test_N3g2_Input() throws IOException {
        InputDto expected = new InputDto();
        expected.movingPieceToken = 'N';
        expected.destinationRow = 2;
        expected.destinationCol = 'G';
        expected.originRow = 3;
        testCase("n3g2", expected);
    }

    @Test
    void test_Qb2e7_Input() throws IOException {
        InputDto expected = new InputDto();
        expected.movingPieceToken = 'Q';
        expected.destinationRow = 7;
        expected.destinationCol = 'E';
        expected.originCol = 'B';
        expected.originRow = 2;
        testCase("qb2e7", expected);
    }

    @Test
    void test_Rde7_Input() throws IOException {
        InputDto expected = new InputDto();
        expected.movingPieceToken = 'R';
        expected.destinationRow = 7;
        expected.destinationCol = 'E';
        expected.originCol = 'D';
        testCase("rde7", expected);
    }

    @Test
    void test_a1PawnPromotion_Input() throws IOException {
        InputDto expected = new InputDto();
        expected.destinationRow = 1;
        expected.destinationCol = 'A';
        expected.pawnPromotionToken = 'Q';
        testCase("a1=Q", expected);
    }

    @Test
    void test_e4_Input() throws IOException {
        InputDto expected = new InputDto();
        expected.destinationRow = 4;
        expected.destinationCol = 'E';
        testCase("e4", expected);
    }
}
