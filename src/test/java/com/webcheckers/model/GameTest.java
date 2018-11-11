package com.webcheckers.model;

import com.webcheckers.model.Game.Turn;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class GameTest {

    private static final String RED_NAME = "redPlayerName";
    private static final String WHITE_NAME = "whitePlayerName";
    private static final Turn CUSTOM_GAME_TURN = Turn.WHITE;

    // Component Under Test
    private Game CuT;

    // Friendly components
    private Player redPlayer;
    private Player whitePlayer;
    private Board board;

    @BeforeEach
    public void setup() {
        redPlayer = new Player(RED_NAME);
        whitePlayer = new Player(WHITE_NAME);
        CuT = new Game(redPlayer, whitePlayer);
    }

    @AfterEach
    public void tearDown(){
        redPlayer = null;
        whitePlayer = null;
        CuT = null;
    }

    @Test
    public void testRegularConstructor() {
        Assertions.assertNotNull(CuT.redPlayer);
        Assertions.assertEquals(redPlayer, CuT.redPlayer);
        Assertions.assertNotNull(CuT.whitePlayer);
        Assertions.assertEquals(whitePlayer, CuT.whitePlayer);
        Assertions.assertNotNull(CuT.board);
    }

    @Test
    public void testCustomConfigConstructor() {
        board = new Board();
        CuT = new Game(redPlayer, whitePlayer, CUSTOM_GAME_TURN, board);

        Assertions.assertNotNull(CuT.redPlayer);
        Assertions.assertEquals(redPlayer, CuT.redPlayer);
        Assertions.assertNotNull(CuT.whitePlayer);
        Assertions.assertEquals(whitePlayer, CuT.whitePlayer);
        Assertions.assertNotNull(CuT.board);
        Assertions.assertEquals(board, CuT.board);
        Assertions.assertEquals(CuT.turn, CUSTOM_GAME_TURN);
    }

    @Test
    public void testGetRedPlayer() {
        Assertions.assertEquals(redPlayer, CuT.getRedPlayer());
    }

    @Test
    public void testGetWhitePlayer() {
        Assertions.assertEquals(whitePlayer, CuT.getWhitePlayer());
    }

    @Test
    public void testIsRedPlayersTurn() {
        // This should be true because game is initiated with
        // red players turn
        Assertions.assertTrue(CuT.isPlayersTurn(redPlayer));
    }

    @Test
    public void restIsRedWhitePlayersTurn() {
        // This should be false because game is initiated with
        // red players turn
        Assertions.assertFalse(CuT.isPlayersTurn(whitePlayer));
    }

    @Test
    public void testGetBoard() {
        board = new Board();
        CuT = new Game(redPlayer, whitePlayer, Turn.RED, board);
        Assertions.assertEquals(board, CuT.getBoard());
    }

    @Test
    public void testGetBoardView() {

    }

    @Test
    public void testGetTurn() {
        board = new Board();
        CuT = new Game(redPlayer, whitePlayer, CUSTOM_GAME_TURN, board);
        Assertions.assertEquals(CUSTOM_GAME_TURN, CuT.getTurn());
    }

    @Test
    public void testSwitchTurn() {
        board = new Board();
        CuT = new Game(redPlayer, whitePlayer, CUSTOM_GAME_TURN, board);
        Turn previous = CuT.getTurn();
        CuT.switchTurn();
        Assertions.assertNotEquals(previous, CuT.getTurn());
        previous = CuT.getTurn();
        CuT.switchTurn();
        Assertions.assertNotEquals(previous, CuT.getTurn());
    }

    @Test
    public void testApplyTurnMoves() {

    }



}
