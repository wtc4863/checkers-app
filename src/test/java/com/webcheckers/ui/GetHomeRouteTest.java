package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.ArrayList;

import static com.webcheckers.ui.WebServer.GAME_URL;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
public class GetHomeRouteTest {

    //
    // Constants
    //
    private static final String SESSION_ID = "12345";
    private static final String USERNAME = "hello";
    private static final String OTHER_USERNAME = "test";

    //
    // Attributes
    //
    private Request request;
    private Response response;
    private Session session;
    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;
    private Player player;
    private GetHomeRoute CuT;

    //
    // Setup
    //
    @BeforeEach
    public void setUp() {
        // Set up the session
        session = mock(Session.class);
        when(session.id()).thenReturn(SESSION_ID);

        // Set up the request
        request = mock(Request.class);
        when(request.session()).thenReturn(session);

        // Set up the response
        response = mock(Response.class);

        // Set up the TemplateEngine
        templateEngine = mock(TemplateEngine.class);

        // Create a player
        player = mock(Player.class);

        // Set up the PlayerLobby
        playerLobby = mock(PlayerLobby.class);
        when(playerLobby.getPlayerBySessionID(SESSION_ID)).thenReturn(player);

        // Set up the route component
        CuT = new GetHomeRoute(playerLobby, templateEngine);
    }

    //
    // Tests
    //

    /**
     * Make sure that if a player is in a game, they will be redirected to the
     * game page.
     */
    @Test
    public void testPlayerInGameRedirect() {
        // Make the player lobby return a player
        when(playerLobby.getPlayerBySessionID(SESSION_ID)).thenReturn(player);

        // Make sure the player lobby will return some sort of game
        Game mockGame = mock(Game.class);
        mockGame.state = Game.State.ACTIVE;
        when(playerLobby.getGame(player)).thenReturn(mockGame);

        assertThrows(spark.HaltException.class, () -> {
            CuT.handle(request, response);
        });
        verify(response).redirect(GAME_URL);
    }

    /**
     * Make sure that, when a user is logged in and there is another user
     * logged in, then the other user's username will appear in a button on the
     * homepage.
     */
    @Test
    public void testPlayerNotInGameSignedInPlayers() {
        // Make it look like we aren't in a game
        when(playerLobby.getGame(player)).thenReturn(null);

        // Make it look like the player is signed in
        when(playerLobby.getPlayerNameBySessionID(SESSION_ID)).thenReturn(USERNAME);

        // Pretend there's someone else signed in
        ArrayList<String> players = new ArrayList<>();
        players.add(USERNAME);
        players.add(OTHER_USERNAME);
        when(playerLobby.getSignedInPlayers()).thenReturn(players);

        // Prepare the template engine tester
        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Set up expected signed in ArrayList
        ArrayList<String> expectedSignedInPlayers = new ArrayList<>();
        expectedSignedInPlayers.add(OTHER_USERNAME);

        // Invoke test
        CuT.handle(request, response);

        // Analyze results
        // Model is a non-null map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        // Model contains the correct View-Model data
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, "");
        testHelper.assertViewModelAttribute(GetHomeRoute.SIGNED_IN_PLAYERS, expectedSignedInPlayers);
        testHelper.assertViewModelAttribute(GetHomeRoute.IS_SIGNED_IN, true);
        testHelper.assertViewModelAttributeIsAbsent(GetHomeRoute.NUM_SIGNED_IN);
        // Test view name
        testHelper.assertViewName(GetHomeRoute.TEMPLATE_NAME);
    }

    /**
     * Make sure that, when a user is not signed in but there are other users
     * signed in, an accurate count
     */
    @Test
    public void testOneUserPlayerCount() {
        // Make it look like we aren't in a game
        when(playerLobby.getGame(any())).thenReturn(null);

        // Make it look like we aren't signed in
        when(playerLobby.getPlayerNameBySessionID(any())).thenReturn(null);

        // Make it look like one player is signed in
        ArrayList<String> expectedSignedInPlayers = new ArrayList<>();
        expectedSignedInPlayers.add(OTHER_USERNAME);
        when(playerLobby.getSignedInPlayers()).thenReturn(expectedSignedInPlayers);

        // Prepare the template engine tester
        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke test
        CuT.handle(request, response);

        // Analyze results
        // Model is a non-null map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        // Model contains the correct View-Model data
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, "");
        testHelper.assertViewModelAttribute(GetHomeRoute.IS_SIGNED_IN, false);
        testHelper.assertViewModelAttribute(GetHomeRoute.NUM_SIGNED_IN, 1);
        testHelper.assertViewModelAttributeIsAbsent(GetHomeRoute.SIGNED_IN_PLAYERS);
        // Test view name
        testHelper.assertViewName(GetHomeRoute.TEMPLATE_NAME);
    }
}
