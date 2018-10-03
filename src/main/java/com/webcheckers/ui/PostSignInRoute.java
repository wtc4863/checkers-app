package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI controller to handle POST sign in requests.
 *
 * @author <a href='mailto:sxn6296@rit.edu'>Sean Newman</a>
 */
public class PostSignInRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    // Attributes
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the {@code POST /signin} HTTP
     * request.
     *
     * @param playerLobby The model that handles player-tracking
     * @param templateEngine The HTML template rendering engine
     */
    public PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        // Validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;

        LOG.config("PostSignInRoute is initialized.");
    }

    /**
     * Invoked when a POST request is made to "/signin"
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return
     *      a redirect to the home page if the sign in was successful,
     *      otherwise the sign in page is returned with an error.
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSignInRoute is invoked.");
        return null;
    }
}
