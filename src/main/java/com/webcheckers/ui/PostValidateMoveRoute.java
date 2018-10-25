package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.Objects;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    //
    // Constants
    //
    public static final String ERROR_MESSAGE = "The username you entered is either already in use, or invalid.  Please try again.";

    //
    // Attributes
    //
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the {@code POST /signin} HTTP
     * request.
     *
     * @param playerLobby The model that handles player-tracking
     * @param templateEngine The HTML template rendering engine
     */
    public PostValidateMoveRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        // Validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;

        LOG.config("PostSignInRoute is initialized.");
    }


    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        return templateEngine.render(new ModelAndView(vm, GetSignInRoute.TEMPLATE_NAME));

    }


}
