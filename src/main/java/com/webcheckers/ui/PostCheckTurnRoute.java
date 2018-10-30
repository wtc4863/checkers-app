package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import java.util.Objects;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostCheckTurnRoute implements Route {
    private static Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    private final PlayerLobby playerLobby;

    public PostCheckTurnRoute(PlayerLobby playerLobby) {
        Objects.requireNonNull(playerLobby, "playLobby must not be null");

        this.playerLobby = playerLobby;
        LOG.config("PostCheckTurnRoute is initialized");
    }

    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();

        return null;
    }

}
