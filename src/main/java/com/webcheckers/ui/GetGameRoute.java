package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GetGameRoute implements Route{
    final static String CURRENT_PLAYER_ATTR = "currentPlayer";
    final static String WHITE_PLAYER_ATTR = "whitePlayer";
    final static String RED_PLAYER_ATTR = "redPlayer";
    final static String ACTIVE_COLOR_ATTR = "activeColor";
    final static String BOARD_VIEW_ATTR = "board";
    final static String MESSAGE_ATTR = "message";

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    GetGameRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
        //validate
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(playerLobby, "playerLobby cannot be null");
        //
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();

        final Map<String, Object> vm = new HashMap<>();
        vm.put(RED_PLAYER_ATTR, playerLobby.getBySessionID(httpSession.id()));


        return null;
    }
}
