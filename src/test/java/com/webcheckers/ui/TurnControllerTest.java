package com.webcheckers.ui;

import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("UI-Tier")
public class TurnControllerTest {

    // Component Under Test
    private TurnController CuT;

    // Friendly Objects
    private Game modelGame;
    private BoardView boardView;
    private Player whitePlayer;
    private Player redPlayer;
    private Board testModelBoard;
    private BoardView testViewBoard;

    // Mocked Objects

    @BeforeEach
    public void setup() {
        testModelBoard = new Board();
        testViewBoard = new BoardView(testModelBoard, false);
        whitePlayer = new Player("whitePlayerName", "1");
        redPlayer = new Player("redPlayerName", "2");
        Game game = new Game(redPlayer, whitePlayer);
        CuT = new TurnController(game, testViewBoard);
    }

    @AfterEach
    public void tearDown() {
        CuT = null;
    }

    @Test
    public void testFromUItoModel() {
    }


}
