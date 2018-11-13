package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import java.util.ArrayList;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class GetGameRoute implements Route{
    //
    // Constants
    //
    final static String CURRENT_PLAYER_ATTR = "currentPlayer";
    final static String WHITE_PLAYER_ATTR = "whitePlayer";
    final static String RED_PLAYER_ATTR = "redPlayer";
    final static String ACTIVE_COLOR_ATTR = "activeColor";
    final static String BOARD_VIEW_ATTR = "board";
    final static String VIEW_MODE_ATTR = "viewMode";
    final static String MESSAGE_ATTR = "message";
    final static String TEMPLATE_NAME = "game.ftl";
    final static String TITLE_ATTR = "title";
    final static String TITLE = "Game";

    final static String SIGNED_IN_PLAYERS = "signedInPlayers";
    final static String IS_SIGNED_IN = "isUserSignedIn";

    final static String PLAYER_IN_GAME_MSG = "Requested player is already in a game. Choose another player.";
    final static String NO_USERNAME_SELECTED = "You are not in a game. You must first start a game with another player.";

    public enum View {
        PLAY, SPECTATOR, REPLAY;
    }

    //
    // Attributes
    //
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    //
    // Constructor
    //
    GetGameRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        //validate
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(playerLobby, "playerLobby cannot be null");
        //
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    //
    // Methods
    //

    private String renderGame(Game game, Player currentPlayer) {
        // Template set-up
        final Map<String, Object> vm = new HashMap<>();

        /*
        If the current player is the white player in the game, then flip the
        board the other way around.
         */
        boolean opposite = currentPlayer.equals(game.getWhitePlayer());

        // Set attributes
        vm.put(TITLE_ATTR, TITLE);
        vm.put(CURRENT_PLAYER_ATTR, currentPlayer);
        vm.put(VIEW_MODE_ATTR, View.PLAY);
        vm.put(RED_PLAYER_ATTR, game.getRedPlayer());
        vm.put(WHITE_PLAYER_ATTR, game.getWhitePlayer());
        vm.put(ACTIVE_COLOR_ATTR, game.getTurn());
        vm.put(BOARD_VIEW_ATTR, game.getBoardView(opposite));

        return templateEngine.render(new ModelAndView(vm, TEMPLATE_NAME));
    }

    private String redirectToHome(Player currentPlayer, String message) {
        // Template set-up
        Map<String, Object> vm = new HashMap<>();

        // Set up list of signed-in players
        ArrayList<String> onlinePlayers = playerLobby.getSignedInPlayers();
        onlinePlayers.remove(currentPlayer.getName());

        // Set attributes
        vm.put(SIGNED_IN_PLAYERS, onlinePlayers);
        vm.put(IS_SIGNED_IN, true);
        vm.put(TITLE_ATTR, GetHomeRoute.TITLE);
        vm.put(MESSAGE_ATTR, new Message(message, Message.MessageType.error));

        return templateEngine.render(new ModelAndView(vm, GetHomeRoute.TEMPLATE_NAME));
    }

  /**
   * Starts a new game and brings the red and white player to the game page
   * @param request the HTTP request
   * @param response the HTTP response
   * @return a rendering of the home or game page
   */
    @Override
    public Object handle(Request request, Response response) {
        // Get the current player
        final Session httpSession = request.session();
        Player thisPlayer = playerLobby.getPlayerBySessionID(httpSession.id());

        // If there is no player, redirect to the home page
        if (thisPlayer == null) {
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        // Get the other player
        Player opponentPlayer = playerLobby.getOpponent(thisPlayer);

        // Check if the players are already in a game with each other
        if (opponentPlayer != null) {
            return renderGame(playerLobby.getGame(thisPlayer), thisPlayer);
        }
        // Players are not in a game with each other, we are starting a new game

        // Make sure they passed a username param
        String username = request.queryParams("username");
        if (username == null) {
            return redirectToHome(thisPlayer, NO_USERNAME_SELECTED);
        } else {
            opponentPlayer = playerLobby.getPlayer(username);
        }

        // Check if the selected opponent is already in a game
        if (playerLobby.getGame(opponentPlayer) == null) {
            // Start new game
            Game game = playerLobby.startGame(thisPlayer, opponentPlayer);
            return renderGame(game, thisPlayer);
        } else {
            return redirectToHome(thisPlayer, PLAYER_IN_GAME_MSG);
        }
    }
}
