package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.mockito.Mockito;
import spark.utils.Assert;

@Tag("Application-Tier")
public class GameCenterTest {

    private GameCenter CuT;
    private Player whitePlayer;
    private Player redPlayer;

    @BeforeEach
    public void setup() {
        // Build GameCenter
        CuT = new GameCenter();
        // create test players for the game
        whitePlayer = new Player("whitePlayerName", "1");
        redPlayer = new Player("redPlayerName", "2");
    }
    @AfterEach
    public void destroy() {
        // Reset for next test
        CuT = null;
    }

    @Test
    public void should_create_GameCenter() {
        assertNotNull(CuT);
    }

    @Test
    public void testStartGame() {
        Game testGame = CuT.startGame(redPlayer, whitePlayer);
        assertNotNull(testGame);
    }

    @Test
    public void get_null_when_player_nonexistent() {
        assertNull(CuT.getOpponent(redPlayer));
    }
    @Test
    public void should_get_opponent_when_player_exists() {
        // must start a game for players to be added to hashmap
        CuT.startGame(redPlayer,whitePlayer);
        assertEquals(whitePlayer, CuT.getOpponent(redPlayer));
    }

    @Test
    public void should_get_null_when_game_nonexistent() {
        assertNull(CuT.getGame(whitePlayer));
    }

    @Test
    public void should_get_game_when_red_player() {
        Game expected = CuT.startGame(redPlayer, whitePlayer);
        Game actual = CuT.getGame(whitePlayer);
        assertSame(expected, actual);
    }

    @Test
    public void should_get_game_when_white_player() {
        Game expected = CuT.startGame(redPlayer, whitePlayer);
        Game actual = CuT.getGame(redPlayer);
        assertSame(expected, actual);
    }

    @Test
    public void should_return_board_view_when_white_player() {
        CuT.startGame(redPlayer, whitePlayer);
        assertNotNull(CuT.getBoardView(whitePlayer));
    }
    @Test
    public void should_return_board_view_when_red_player() {
        CuT.startGame(redPlayer, whitePlayer);
        assertNotNull(CuT.getBoardView(redPlayer));

    }

}
