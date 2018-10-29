package com.webcheckers.ui;

import com.webcheckers.appl.TurnController;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.Message.MessageType;
import java.util.logging.Logger;
import java.util.Objects;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

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

    /**
     * Create the Spark Route (UI controller) for the {@code POST /signin} HTTP
     * request.
     *
     * @param playerLobby The model that handles player-tracking
     */
    public PostValidateMoveRoute(PlayerLobby playerLobby) {
        // Validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");

        this.playerLobby = playerLobby;

        LOG.config("PostValidateMoveRoute is initialized.");
    }


    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        final String moveToBeValidated = request.body();

        TurnController turnController = new TurnController(playerLobby);
        boolean result = turnController.handleValidation(moveToBeValidated, httpSession.id());
        if(result) {
            Message success = new Message(VALID_MOVE_MESSAGE, MessageType.info);
            return turnController.MessageFromModeltoUI(success);
        } else {
            Message error = new Message(INVALID_MOVE_MESSAGE, MessageType.error);
            return turnController.MessageFromModeltoUI(error);
        }
    }


}
