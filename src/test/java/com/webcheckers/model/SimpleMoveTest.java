package com.webcheckers.model;

import com.webcheckers.model.Game.Turn;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Model-Tier")
public class SimpleMoveTest {

    private static final int START_ROW = 1;
    private static final int START_COL = 2;
    private static final int END_ROW_RED = 2;
    private static final int END_COL_RED = 3;
    private static final int END_ROW_WHITE = 0;
    private static final int END_COL_WHITE = 3;
    private static final String TEST_NAME_RED = "red";
    private static final String TEST_NAME_WHITE= "white";

    // Compoenent under test
    private SimpleMove CuT;

    // Friendly Objects
    private Position testStart;
    private Position testEnd;
    private Game testGame;

    @BeforeEach
    public void setup() {
        CuT = new SimpleMove(testStart, testEnd);
        testGame = new Game(new Player(TEST_NAME_RED), new Player(TEST_NAME_WHITE));
    }

    @AfterEach
    public void tearDown() {
        CuT = null;
        testStart = null;
        testEnd = null;
        testGame = null;
    }

    @Test
    public void testExecuteMoveSuccess() {
        boolean result = CuT.executeMove(testGame);
        Turn previousTurn = testGame.getTurn();
        Assertions.assertTrue(result);
        Assertions.assertNotEquals(testGame.getTurn(), previousTurn);
    }

    @Test
    public void testExecuteMoveFailure() {
        CuT = mock(SimpleMove.class);
        when(CuT.validateMove(testGame)).thenReturn(false);
        boolean result = CuT.executeMove(testGame);
        Assertions.assertTrue(result);
    }

    @Test
    public void shouldFailValidateMoveEndSpaceFilled() {
        Game mockedGame  = new Game(new Player(TEST_NAME_RED), new Player(TEST_NAME_WHITE));
        when(mockedGame.getBoard().spaceIsValid(testEnd)).thenReturn(false);
        Assertions.assertFalse(CuT.validateMove(mockedGame));
    }

    @Test
    public void shouldPassWhitePlayerValidateMove() {
        //TODO: test upper left of white
        // set valid starting spot
        testStart = new Position(START_ROW, START_COL);
        // set valid ending spot using white's movement (upper right)
        testEnd = new Position(END_ROW_WHITE, END_COL_WHITE);
        // create a new move to update these values
        CuT = new SimpleMove(testStart, testEnd);
        Game mockedGame  = new Game(new Player(TEST_NAME_RED), new Player(TEST_NAME_WHITE));
        when(mockedGame.getBoard().spaceIsValid(testEnd)).thenReturn(true);
        when(mockedGame.getTurn()).thenReturn(Turn.WHITE);
        Assertions.assertTrue(CuT.validateMove(mockedGame));
    }

    @Test
    public void shouldFailWhitePlayerValidateMove() {
        // set valid starting point
        testStart = new Position(START_ROW, START_COL);
        // set invalid ending point by using red's moving direction (lower right)
        testEnd = new Position(END_ROW_RED, END_COL_RED);
        // update CuT to reflect this change
        CuT = new SimpleMove(testStart, testEnd);
        Game mockedGame  = new Game(new Player(TEST_NAME_RED), new Player(TEST_NAME_WHITE));
        when(mockedGame.getBoard().spaceIsValid(testEnd)).thenReturn(true);
        when(mockedGame.getTurn()).thenReturn(Turn.WHITE);
        Assertions.assertFalse(CuT.validateMove(mockedGame));
    }

    @Test
    public void shouldPassRedPlayerValidateMove() {
        //TODO: test left side of the move
        testStart = new Position(START_ROW, START_COL);
        testEnd = new Position(END_ROW_RED, END_COL_RED);
        CuT = new SimpleMove(testStart, testEnd);
        Game mockedGame  = new Game(new Player(TEST_NAME_RED), new Player(TEST_NAME_WHITE));
        when(mockedGame.getBoard().spaceIsValid(testEnd)).thenReturn(true);
        when(mockedGame.getTurn()).thenReturn(Turn.RED);
        Assertions.assertTrue(CuT.validateMove(mockedGame));
    }
    @Test
    public void shouldFailRedPlayerValidateMove() {
        testStart = new Position(START_ROW, START_COL);
        testEnd = new Position(END_ROW_WHITE, END_COL_WHITE);
        CuT = new SimpleMove(testStart, testEnd);
        Game mockedGame  = new Game(new Player(TEST_NAME_RED), new Player(TEST_NAME_WHITE));
        when(mockedGame.getBoard().spaceIsValid(testEnd)).thenReturn(true);
        when(mockedGame.getTurn()).thenReturn(Turn.RED);
        Assertions.assertFalse(CuT.validateMove(mockedGame));
    }
}
