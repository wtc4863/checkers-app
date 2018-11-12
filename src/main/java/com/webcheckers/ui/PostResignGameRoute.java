package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostResignGameRoute implements Route {

    //
    // Attributes
    //
    private final PlayerLobby playerLobby;

    public PostResignGameRoute(PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        final String sessionID = httpSession.id();

        Player resigningPlayer = playerLobby.getPlayerBySessionID(sessionID);
        Game gameToResignFrom = playerLobby.getGame(resigningPlayer);

        playerLobby.resignFromGame(gameToResignFrom, resigningPlayer);

        // FIXME: make sure to tell the players that they have won or lost


        return null;
    }

}
