package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import spark.Request;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetHomeRouteTest {

    //
    // Constants
    //
    private static final String SESSION_ID = "12345";

    //
    // Attributes
    //
    private Request request;
    private Session session;
    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

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

        // Set up the TemplateEngine
        templateEngine = mock(TemplateEngine.class);

        // Set up the PlayerLobby
        playerLobby = mock(PlayerLobby.class);

        // Set up the route component
        GetHomeRoute CuT = new GetHomeRoute(playerLobby, templateEngine);
    }

    //
    // Tests
    //
}
