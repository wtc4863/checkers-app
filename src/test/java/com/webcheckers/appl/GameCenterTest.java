package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

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
        whitePlayer = null;
        redPlayer = null;
    }

    @Test
    public void testGameCenterConstructor() {
        assertNotNull(CuT.activeGames);
        assertNotNull(CuT.opponents);
    }

    @Test
    public void testStartGame() {
        Game testGame = CuT.startGame(redPlayer, whitePlayer);
        int prev_active_size = CuT.activeGames.size();
        int prev_num_opp = CuT.opponents.size();
        // check if the list and hashmap updated with number of player and games
        assertEquals(prev_active_size, CuT.activeGames.size());
        assertEquals(prev_num_opp, CuT.opponents.size());
        // check if game was returned
        assertNotNull(testGame);
    }

    @Test
    public void testGetOpponentWithNonexistentPlayer() {
        assertNull(CuT.getOpponent(redPlayer));
    }
    @Test
    public void testGetOpponentWithExistingPlayer() {
        // must start a game for players to be added to hashmap
        CuT.startGame(redPlayer,whitePlayer);
        assertEquals(whitePlayer, CuT.getOpponent(redPlayer));
    }

    @Test
    public void testGetGameWithNonexistentPlayer() {
        // should return null because no game found with that player
        assertNull(CuT.getGame(whitePlayer));
    }

    @Test
    public void testGetGameWithExistingWhitePlayer() {
        Game expected = CuT.startGame(redPlayer, whitePlayer);
        Game actual = CuT.getGame(whitePlayer);
        assertSame(expected, actual);
    }

    @Test
    public void testGetGameWithExistingRedPlayer() {
        Game expected = CuT.startGame(redPlayer, whitePlayer);
        Game actual = CuT.getGame(redPlayer);
        assertSame(expected, actual);
    }

    @Test
    public void testGetBoardViewWithExistingWhitePlayer() {
        CuT.startGame(redPlayer, whitePlayer);
        assertNotNull(CuT.getBoardView(whitePlayer));
    }

    @Test
    public void testGetBoardViewWithExistingRedPlayer() {
        CuT.startGame(redPlayer, whitePlayer);
        assertNotNull(CuT.getBoardView(redPlayer));

    }

}
