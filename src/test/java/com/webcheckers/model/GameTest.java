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
    private static final Turn CUSTOM_CONFIG_GAME_TURN = Turn.WHITE;

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
        Assertions.assertNotNull(redPlayer);
        Assertions.assertEquals(redPlayer, CuT.redPlayer);
        Assertions.assertNotNull(whitePlayer);
        Assertions.assertEquals(whitePlayer, CuT.whitePlayer);
        Assertions.assertNotNull(board);
        Assertions.assertEquals(board, CuT.board);
    }

    @Test
    public void testCustomConfigConstructor() {
        redPlayer = new Player(RED_NAME);
        whitePlayer = new Player(WHITE_NAME);
        board = new Board();
        CuT = new Game(redPlayer, whitePlayer, CUSTOM_CONFIG_GAME_TURN, board);

        Assertions.assertNotNull(redPlayer);
        Assertions.assertEquals(redPlayer, CuT.redPlayer);
        Assertions.assertNotNull(whitePlayer);
        Assertions.assertEquals(whitePlayer, CuT.whitePlayer);
        Assertions.assertNotNull(board);
        Assertions.assertEquals(board, CuT.board);
        Assertions.assertEquals(CuT.turn, CUSTOM_CONFIG_GAME_TURN);
    }

    @Test
    public void testGetRedPlayer() {

    }

    @Test
    public void testGetWhitePlayer() {

    }

    @Test
    public void testIsPlayersTurn() {

    }

    @Test
    public void testGetBoar() {

    }

    @Test
    public void testGetBoardView() {

    }

    @Test
    public void testGetTurn() {

    }

    @Test
    public void testSwitchTurn() {

    }

    @Test
    public void testApplyTurnMoves() {

    }



}
