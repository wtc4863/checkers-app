package com.webcheckers.appl;

import com.webcheckers.model.*;
import com.webcheckers.ui.BoardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Class that keeps track of all of the games currently active, and the opponents that are currently
 * matched up
 */

public class GameCenter {
    // Case-based signin names
    private static final String KING_PIECE = "test king";
    private static final String DOUBLE_JUMP = "test double";
    private static final String DOUBLE_JUMP_KING = "test double king";
    private static final String NO_MOVES_AVAILABLE = "test no moves";
    private static final String KING_MID_TURN = "test king middle";
    private static final String EASY_WIN = "test easy win";
    private static final ArrayList<String> customNames = new ArrayList<>(Arrays.asList(
        KING_PIECE,
        DOUBLE_JUMP,
        DOUBLE_JUMP_KING,
        NO_MOVES_AVAILABLE
    ));

    //
    // Attributes
    //
   ArrayList<Game> activeGames;
    HashMap<String, Player> opponents;

    //
    // Constructor
    //
    public GameCenter() {
        this.activeGames = new ArrayList<>();
        this.opponents = new HashMap<>();
    }

    /** Constructor used for testing */
    public GameCenter(HashMap<String, Player> opponents, ArrayList<Game> activeGames) {
        this.opponents = opponents;
        this.activeGames = activeGames;
    }

    //
    // Methods
    //
    /**
     * Gets the opponent player of a specified player, if there is one
     * @param player player whose opponent is being found
     * @return player object of opponent player
     */
    public Player getOpponent(Player player) {
        return opponents.get(player.getName());
    }

    /**
     * Gets the game object that a specified player is playing in
     * @param player player that is playing in the desired game
     * @return game object
     */
    public Game getGame(Player player) {
        for (Game game : activeGames) {
            if (player.equals(game.getRedPlayer()) || player.equals(game.getWhitePlayer())) {
                return game;
            }
        }
        return null;
    }

    /**
     * Determines if the requested BoardView is for the white or red player, and passes the call
     * to the game object of the specified player
     * @param player player that the BoardView is for
     * @return BoardView for specified player
     */
    public BoardView getBoardView(Player player) {
        Game game = getGame(player);
        if(game.getWhitePlayer() == player) {
            return game.getBoardView(true);
        }
        return game.getBoardView(false);
    }

    /**
     * Starts a new game with two players and matches them up in the opponents map
     * @param redPlayer
     * @param whitePlayer
     * @return
     */
    public synchronized Game startGame(Player redPlayer, Player whitePlayer) {
        String redName = redPlayer.getName();
        String whiteName = whitePlayer.getName();
        this.opponents.put(redName, whitePlayer);
        this.opponents.put(whiteName, redPlayer);
        Game game = new Game(redPlayer, whitePlayer);
        if (customNames.contains(redName)) {
            switch (redName) {
                case KING_PIECE:
                    game = Game.testKingPieces(redPlayer, whitePlayer);
                    break;
                case DOUBLE_JUMP:
                    game = Game.testDoubleJump(redPlayer, whitePlayer);
                    break;
                case DOUBLE_JUMP_KING:
                    game = Game.testDoubleJumpKing(redPlayer, whitePlayer);
                    break;
                case NO_MOVES_AVAILABLE:
                    game = Game.testNoMoves(redPlayer, whitePlayer);
                    break;
                case KING_MID_TURN:
                    game = Game.testKingMidTurn(redPlayer, whitePlayer);
                    break;
                case EASY_WIN:
                    game = Game.testCaptureToEnd(redPlayer, whitePlayer);
                    break;
            }
        }
        activeGames.add(game);
        return game;
    }

    /**
     * End a game by removing the game from the list of active games and
     * deleting the opponent match-up.
     *
     * @param game the game to end
     */
    synchronized void endGame(Game game) {
        if (game.getState() == Game.State.ACTIVE) {
            game.setStateEnded();
        } else {
            this.opponents.remove(game.getRedPlayer().getName());
            this.opponents.remove(game.getWhitePlayer().getName());
            this.activeGames.remove(game);
        }
    }

    /*
     * Removes game from the game center and removes the pairing from the hashmap. This will effectively
     * delete the game from the server so it no longer exists.
     * @param gameToResignFrom the game from which the player is resigning
     * @param playerThatsResigning the player that is resigning
     */
    public synchronized void resignFromGame(Game gameToResignFrom, Player playerThatsResigning) {
        gameToResignFrom.leaveFromGame(playerThatsResigning);
        endGame(gameToResignFrom);
    }

}
