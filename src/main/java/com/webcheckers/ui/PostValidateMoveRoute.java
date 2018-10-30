package com.webcheckers.ui;

import com.webcheckers.appl.TurnController;
import com.webcheckers.appl.PlayerLobby;
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

    //
    // Attributes
    //
    final PlayerLobby playerLobby;

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
        Message res = turnController.handleValidation(moveToBeValidated, httpSession.id());
        return turnController.MessageFromModeltoUI(res);

    }

    /**
     * Get player lobby should only be used for test purposes
     * @return the playerLobby attribute
     */
    PlayerLobby getPlayerLobby() {
        return playerLobby;
    }
}
