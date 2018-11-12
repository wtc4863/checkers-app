package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.Route;

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
        return null;
    }

}
