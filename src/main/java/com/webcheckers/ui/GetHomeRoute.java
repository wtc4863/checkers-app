package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    // Model-View constants
    static final String TITLE_ATTR = "title";
    static final String SIGNED_IN_PLAYERS = "signedInPlayers";

    // keys for the session-related items
    static final String PLAYER_SESSION_KEY = "playerSession";

    // Attributes
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param playerLobby    The backend model that will be the master model for the game
     * @param templateEngine the HTML template rendering engine
     */
    public GetHomeRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        //
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetHomeRoute is invoked.");

        // retrieve the http session
        final Session httpSession = request.session();

        // start building the view-model
        Map<String, Object> vm = new HashMap<>();

        vm.put(TITLE_ATTR, "Welcome!");

        // list of signed in players to render to the user
        //TODO: remove yourself from displayed users
        vm.put(SIGNED_IN_PLAYERS, playerLobby.getSignedInPlayers());

        return templateEngine.render(new ModelAndView(vm, "home.ftl"));
    }

}