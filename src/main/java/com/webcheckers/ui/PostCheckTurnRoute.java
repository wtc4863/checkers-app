package com.webcheckers.ui;

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

    private final PlayerLobby playerLobby;
    private final Gson gson;

    public PostCheckTurnRoute(PlayerLobby playerLobby) {
        Objects.requireNonNull(playerLobby, "playLobby must not be null");

        this.playerLobby = playerLobby;
        GsonBuilder gsonBuilder = new GsonBuilder();
        this.gson = gsonBuilder.create();

        LOG.config("PostCheckTurnRoute is initialized");
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostCheckTurnRoute invoked");
        final Session httpSession = request.session();
        final String sessionID = httpSession.id();

        Player currentPlayer = playerLobby.getPlayerBySessionID(sessionID);
        Game currentGame = playerLobby.getGame(currentPlayer);

        boolean checkTurnResult = currentGame.isPlayersTurn(currentPlayer);
        LOG.finer("Player who's turn is being checked for:" + currentPlayer.toString());
        LOG.finer("Result: " + Boolean.toString(checkTurnResult));

        String text = String.format("%s", Boolean.toString(checkTurnResult));
        return gson.toJson(new Message(text, MessageType.info), Message.class);
    }

}
