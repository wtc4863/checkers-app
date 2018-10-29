package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.ui.Message.MessageType;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.Objects;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    //
    // Constants
    //
    public static final String INVALID_MOVE_MESSAGE = "Invalid Move";
    public static final String VALID_MOVE_MESSAGE = "Valid Move";

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

        LOG.config("PostValidateMoveRoute is initialized.");
    }


    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        final String moveToBeValidated = request.body();

        TurnController turnController = new TurnController(playerLobby);
        boolean result = turnController.handleValidation(moveToBeValidated, httpSession.id());
        if(result) {
            Message success = new Message(VALID_MOVE_MESSAGE, MessageType.INFO);
            return turnController.MessageFromModeltoUI(success);
        } else {
            Message error = new Message(INVALID_MOVE_MESSAGE, MessageType.ERROR);
            return turnController.MessageFromModeltoUI(error);
        }

    }


}
