package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

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
        Player thisPlayer = playerLobby.getBySessionID(httpSession.id());
        Player opponentPlayer;
        Player redPlayer = null;
        Player whitePlayer;

        //if current player is being selected by someone else
        if(request.queryParams("username") == null) {
            opponentPlayer = GameCenter.getOpponent(thisPlayer);
        }

        //the current player is selecting another player, thus making them the red player
        else {
            opponentPlayer = PlayerLobby.getPlayer(request.queryParams("username"));
            redPlayer = thisPlayer;
        }

        //if the current player is not in a game but the opponent is, reject the request
        if (GameCenter.getGame(thisPlayer) == null && GameCenter.getGame(opponentPlayer) != null) {
            response.redirect("");
            halt();
            return null;
        }

        //if the current player is red, make the other player white and start the game
        if (thisPlayer.equals(redPlayer)) {
            whitePlayer = opponentPlayer;
            GameCenter.startGame(redPlayer, whitePlayer);
        }

        //the current player is the white player and the other player is the red
        else {
            whitePlayer = thisPlayer;
            redPlayer = opponentPlayer;
        }


        vm.put(WHITE_PLAYER_ATTR, whitePlayer);
        vm.put(RED_PLAYER_ATTR, redPlayer);
        vm.put(CURRENT_PLAYER_ATTR, thisPlayer);
        vm.put(BOARD_VIEW_ATTR, GameCenter.getGame(thisPlayer).getBoard().getBoardViewVersion());


        return null;
    }
}
