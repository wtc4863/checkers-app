package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Game;
import com.webcheckers.model.Game.State;
import com.webcheckers.model.Player;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        assertEquals(1, CuT.gameID);
        assertEquals(0, CuT.readGameID(redPlayer));
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

    @Test
    public void testResignFromGameWhenActive() {
        // create datastructures to use within GameCenter
        HashMap<String, Player> testHashMap = new HashMap<>();
        testHashMap.put(redPlayer.getName(), whitePlayer);
        testHashMap.put(whitePlayer.getName(), redPlayer);
        ArrayList<Game> testGames = new ArrayList<>();
        Game game = mock(Game.class);
        when(game.getState()).thenReturn(State.ACTIVE);
        testGames.add(game);

        CuT = new GameCenter(testHashMap, testGames);
        CuT.resignFromGame(game, redPlayer);
        verify(game, times(1)).leaveFromGame(redPlayer);
        // when in active game, it just sets state to null, next call it is wiped
        assertEquals(1, testGames.size());
        assertEquals(2, testHashMap.size());
    }

    @Test
    public void testResignFromGameWhenEnded() {
        // create datastructures to use within GameCenter
        HashMap<String, Player> testHashMap = new HashMap<>();
        testHashMap.put(redPlayer.getName(), whitePlayer);
        testHashMap.put(whitePlayer.getName(), redPlayer);
        ArrayList<Game> testGames = new ArrayList<>();
        Game game = mock(Game.class);
        when(game.getState()).thenReturn(State.ENDED);
        when(game.getRedPlayer()).thenReturn(redPlayer);
        when(game.getWhitePlayer()).thenReturn(whitePlayer);
        testGames.add(game);

        CuT = new GameCenter(testHashMap, testGames);
        CuT.resignFromGame(game, redPlayer);
        verify(game, times(1)).leaveFromGame(redPlayer);
        // when in active game, it just sets state to null, next call it is wiped
        assertEquals(0, testGames.size());
        assertEquals(0, testHashMap.size());
    }

}
