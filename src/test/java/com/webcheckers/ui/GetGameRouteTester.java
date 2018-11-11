package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
public class GetGameRouteTester {

    //
    // Constants
    //
    private static final String SESSION_ID = "12345";
    private static final String OPPONENT_USERNAME = "other";
    private static final String MY_USERNAME = "jimmy";

    //
    // Attributes
    //
    private Session session;
    private Request request;
    private Response response;
    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;
    private GetGameRoute CuT;
    private Player thisPlayer;
    private Player otherPlayer;
    private Game game;

    //
    // Setup
    //
    @BeforeEach
    public void setUp() {
        // Set up session
        session = mock(Session.class);
        when(session.id()).thenReturn(SESSION_ID);

        // Set up request
        request = mock(Request.class);
        when(request.session()).thenReturn(session);

        // Set up response
        response = mock(Response.class);

        // Set up template engine
        templateEngine = mock(TemplateEngine.class);

        // Set up the user making the request
        thisPlayer = mock(Player.class);
        when(thisPlayer.getName()).thenReturn(MY_USERNAME);

        // Set up the other user, i.e. the opponent
        otherPlayer = mock(Player.class);

        // Set up player lobby
        playerLobby = mock(PlayerLobby.class);
        when(playerLobby.getPlayerBySessionID(SESSION_ID)).thenReturn(thisPlayer);

        // Set up game
        game = mock(Game.class);
        when(game.getTurn()).thenReturn(Game.Turn.RED);

        // Set up the route component
        CuT = new GetGameRoute(playerLobby, templateEngine);
    }

    //
    // Tests
    //

    /**
     * Make sure that a user cannot start a game with someone else when the
     * other user is already in a game.
     */
    @Test
    public void testStartGameOpponentInGame() {
        // Make it look like we're starting the game
        when(request.queryParams("username")).thenReturn(OPPONENT_USERNAME);

        // Return the other player when requested
        when(playerLobby.getPlayer(OPPONENT_USERNAME)).thenReturn(otherPlayer);

        // Make it look like we're not in a game but they are
        when(playerLobby.getGame(thisPlayer)).thenReturn(null);
        when(playerLobby.getGame(otherPlayer)).thenReturn(mock(Game.class));

        // Return the list of signed in players
        ArrayList<String> allPlayers = new ArrayList<>();
        allPlayers.add(MY_USERNAME);
        allPlayers.add(OPPONENT_USERNAME);
        when(playerLobby.getSignedInPlayers()).thenReturn(allPlayers);

        // Set up template engine tester
        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Set up expected signed in ArrayList
        ArrayList<String> expectedSignedInPlayers = new ArrayList<>();
        expectedSignedInPlayers.add(OPPONENT_USERNAME);

        // Invoke test
        CuT.handle(request, response);

        // Analyze results
        // Model is a non-null map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        // Model contains the correct View-Model data
        testHelper.assertViewModelAttribute(GetGameRoute.SIGNED_IN_PLAYERS, expectedSignedInPlayers);
        testHelper.assertViewModelAttribute(GetGameRoute.IS_SIGNED_IN, true);
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttributeIsAbsent(GetGameRoute.WHITE_PLAYER_ATTR);
        testHelper.assertViewModelAttributeIsAbsent(GetGameRoute.RED_PLAYER_ATTR);
        testHelper.assertViewModelAttributeIsAbsent(GetGameRoute.CURRENT_PLAYER_ATTR);
        testHelper.assertViewModelAttributeIsAbsent(GetGameRoute.ACTIVE_COLOR_ATTR);
        testHelper.assertViewModelAttributeIsAbsent(GetGameRoute.BOARD_VIEW_ATTR);
        testHelper.assertViewModelAttributeIsAbsent(GetGameRoute.VIEW_MODE_ATTR);
        // Test view name
        testHelper.assertViewName(GetHomeRoute.TEMPLATE_NAME);
    }

    /**
     * Make sure that a user can start a game with another logged-in user when
     * the other user is not currently in a game.
     */
    @Test
    public void testStartGameOpponentNotInGame() {
        // Make it look like we're starting the game
        when(request.queryParams("username")).thenReturn(OPPONENT_USERNAME);

        // Return the other player when requested
        when(playerLobby.getPlayer(OPPONENT_USERNAME)).thenReturn(otherPlayer);

        // Make it look like neither player is in a game
        when(playerLobby.getGame(thisPlayer)).thenReturn(null);
        when(playerLobby.getGame(otherPlayer)).thenReturn(null);

        // Start the game
        when(playerLobby.startGame(thisPlayer, otherPlayer)).thenReturn(game);

        // Set correct player positions
        when(game.getWhitePlayer()).thenReturn(otherPlayer);
        when(game.getRedPlayer()).thenReturn(thisPlayer);

        // Set up template engine tester
        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Set up the expected board view
        BoardView expected = mock(BoardView.class);
        when(playerLobby.getBoardView(thisPlayer)).thenReturn(expected);
        when(game.getBoardView(false)).thenReturn(expected);

        // Invoke test
        CuT.handle(request, response);

        // Analyze results
        // Model is a non-null map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        // Model contains the correct View-Model data
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE_ATTR, GetGameRoute.TITLE);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER_ATTR, otherPlayer);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER_ATTR, thisPlayer);
        testHelper.assertViewModelAttribute(GetGameRoute.CURRENT_PLAYER_ATTR, thisPlayer);
        testHelper.assertViewModelAttribute(GetGameRoute.BOARD_VIEW_ATTR, expected);
        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_MODE_ATTR, GetGameRoute.View.PLAY);
        testHelper.assertViewModelAttributeIsAbsent(GetGameRoute.SIGNED_IN_PLAYERS);
        testHelper.assertViewModelAttributeIsAbsent(GetGameRoute.IS_SIGNED_IN);
        testHelper.assertViewModelAttributeIsAbsent(GetGameRoute.MESSAGE_ATTR);
        // Test view name
        testHelper.assertViewName(GetGameRoute.TEMPLATE_NAME);
    }

    /**
     * Make sure that the player will be properly pulled into the game when
     * another user has started a game with them.
     */
    @Test
    public void testOtherPlayerStartedGame() {
        // Make it look like someone else has already started the game
        when(request.queryParams("username")).thenReturn(null);
        when(playerLobby.getOpponent(thisPlayer)).thenReturn(otherPlayer);

        // Make it look like neither player is in a game
        when(playerLobby.getGame(thisPlayer)).thenReturn(null);
        when(playerLobby.getGame(otherPlayer)).thenReturn(null);

        // Get the already-started game
        when(playerLobby.getGame(thisPlayer)).thenReturn(game);

        // Set correct player positions
        when(game.getWhitePlayer()).thenReturn(thisPlayer);
        when(game.getRedPlayer()).thenReturn(otherPlayer);

        // Set up template engine tester
        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Set up the expected board view
        BoardView expected = mock(BoardView.class);
        when(playerLobby.getBoardView(thisPlayer)).thenReturn(expected);
        when(game.getBoardView(true)).thenReturn(expected);

        // Invoke test
        CuT.handle(request, response);

        // Analyze results
        // Model is a non-null map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        // Model contains the correct View-Model data
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE_ATTR, GetGameRoute.TITLE);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER_ATTR, thisPlayer);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER_ATTR, otherPlayer);
        testHelper.assertViewModelAttribute(GetGameRoute.CURRENT_PLAYER_ATTR, thisPlayer);
        testHelper.assertViewModelAttribute(GetGameRoute.BOARD_VIEW_ATTR, expected);
        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_MODE_ATTR, GetGameRoute.View.PLAY);
        testHelper.assertViewModelAttributeIsAbsent(GetGameRoute.SIGNED_IN_PLAYERS);
        testHelper.assertViewModelAttributeIsAbsent(GetGameRoute.IS_SIGNED_IN);
        testHelper.assertViewModelAttributeIsAbsent(GetGameRoute.MESSAGE_ATTR);
        // Test view name
        testHelper.assertViewName(GetGameRoute.TEMPLATE_NAME);
    }
}
