package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
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

    //
    // Attributes
    //
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    //
    // Constructor
    //
    public PostSubmitTurnRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {

        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    //
    // Methods
    //
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();

        final Session httpSession = request.session();
        final String sessionID = httpSession.id();

        Player thisPlayer = playerLobby.getPlayerBySessionID(sessionID);

        if(thisPlayer == null) {
            response.redirect("/");
            halt();
            return null;
        }

        Game game = playerLobby.getGame(thisPlayer);

        vm.put(GetGameRoute.TITLE_ATTR, "Game");
        vm.put(GetGameRoute.WHITE_PLAYER_ATTR, game.getWhitePlayer());
        vm.put(GetGameRoute.RED_PLAYER_ATTR, game.getRedPlayer());
        vm.put(GetGameRoute.CURRENT_PLAYER_ATTR, thisPlayer);
        vm.put(GetGameRoute.VIEW_MODE_ATTR, GetGameRoute.View.PLAY);

        if(game.movesLeft()) {
            vm.put(GetGameRoute.MESSAGE_ATTR, ERROR_MESSAGE);

        } else {
            game.applyTurnMoves();
            vm.put(GetGameRoute.MESSAGE_ATTR, new Message("Submission successful", Message.MessageType.INFO));
        }

        vm.put(GetGameRoute.ACTIVE_COLOR_ATTR, game.getTurn());
        vm.put(GetGameRoute.BOARD_VIEW_ATTR, playerLobby.getBoardView(thisPlayer));
        return templateEngine.render(new ModelAndView(vm, GetGameRoute.TEMPLATE_NAME));
}
}
