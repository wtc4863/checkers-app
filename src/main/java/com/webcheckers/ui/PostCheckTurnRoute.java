package com.webcheckers.ui;

import static com.webcheckers.ui.WebServer.HOME_URL;
import static spark.Spark.halt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.ui.Message.MessageType;
import java.util.Objects;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostCheckTurnRoute implements Route {
    private static Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    final PlayerLobby playerLobby;
    final Gson gson;

    public PostCheckTurnRoute(PlayerLobby playerLobby, Gson gson) {
        Objects.requireNonNull(playerLobby, "playLobby must not be null");

        this.playerLobby = playerLobby;
        this.gson = new Gson();

        LOG.config("PostCheckTurnRoute is initialized");
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostCheckTurnRoute invoked");
        final Session httpSession = request.session();
        final String sessionID = httpSession.id();

        Player currentPlayer = playerLobby.getPlayerBySessionID(sessionID);
        Game currentGame = playerLobby.getGame(currentPlayer);

        // Check if the game is over
        if (currentGame.state == Game.State.ENDED) {
            return gson.toJson(new Message("true", MessageType.info), Message.class);
        }

        // Check turn is only called within a game, therefore we can check if game == null
        // then we can assume that the other player has resigned
        if (currentGame != null) {
            boolean checkTurnResult = currentGame.isPlayersTurn(currentPlayer);
            LOG.finer("Player who's turn is being checked for:" + currentPlayer.toString());
            LOG.finer("Result: " + Boolean.toString(checkTurnResult));

            String text = String.format("%s", Boolean.toString(checkTurnResult));
            return gson.toJson(new Message(text, MessageType.info), Message.class);
        } else {
            // FIXME: the user isn't getting properly redirected, its immediately going back to get game route
            response.redirect(HOME_URL);
            halt();
            return null;
        }
    }

}
