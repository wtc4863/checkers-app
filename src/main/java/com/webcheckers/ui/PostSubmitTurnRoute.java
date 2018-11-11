package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.TurnController;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.ui.Message.MessageType;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class PostSubmitTurnRoute implements Route{

    //
    // Constants
    //
    private static final String ERROR_MESSAGE = "Submitted turn is incomplete";
    private static final String SUCCESS_MESSAGE = "Turn submitted";

    //
    // Attributes
    //
    private final PlayerLobby playerLobby;

    //
    // Constructor
    //

    public PostSubmitTurnRoute(PlayerLobby playerLobby) {

        Objects.requireNonNull(playerLobby, "playerLobby must not be null");

        this.playerLobby = playerLobby;
    }

    //
    // Methods
    //
    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        final String sessionID = httpSession.id();

        Player thisPlayer = playerLobby.getPlayerBySessionID(sessionID);

        if(thisPlayer == null) {
            response.redirect("/");
            halt();
            return null;
        }

        Game game = playerLobby.getGame(thisPlayer);
        TurnController turnController = new TurnController(playerLobby);
        //TODO: implement what happens when an unfinished turn is submitted
        game.applyTurnMoves();
        turnController.resetMoves();
        return turnController.MessageFromModeltoUI(new Message(SUCCESS_MESSAGE, MessageType.info));

  }
}
