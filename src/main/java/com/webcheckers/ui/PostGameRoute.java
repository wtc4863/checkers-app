package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PostGameRoute implements Route{
    final static String CURRENT_PLAYER_ATTR = "currentPlayer";
    final static String WHITE_PLAYER_ATTR = "whitePlayer";
    final static String RED_PLAYER_ATTR = "redPlayer";
    final static String ACTIVE_COLOR_ATTR = "activeColor";
    final static String BOARD_VIEW_ATTR = "board";
    final static String MESSAGE_ATTR = "message";

    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    PostGameRoute(TemplateEngine templateEngine, PlayerLobby playerLobby) {
        //validate
        Objects.requireNonNull(templateEngine, "templateEngine cannot be null");
        Objects.requireNonNull(playerLobby, "playerLobby cannot be null");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        final Map<String, Object> vm = new HashMap<>();

        return null;
    }


}
