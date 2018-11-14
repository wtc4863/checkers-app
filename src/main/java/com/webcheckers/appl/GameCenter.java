package com.webcheckers.appl;

import com.webcheckers.model.*;
import com.webcheckers.ui.BoardView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that keeps track of all of the games currently active, and the opponents that are currently
 * matched up
 */

public class GameCenter {
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
        this.opponents.put(redPlayer.getName(), whitePlayer);
        this.opponents.put(whitePlayer.getName(), redPlayer);
        Game game = new Game(redPlayer, whitePlayer);
        activeGames.add(game);
        return game;
        //TODO
    }

    /**
     * End a game by removing the game from the list of active games and
     * deleting the opponent match-up.
     *
     * @param game the game to end
     */
    synchronized void endGame(Game game) {
        if (game.state == Game.State.ACTIVE) {
            game.state = Game.State.ENDED;
        } else {
            this.opponents.remove(game.getRedPlayer().getName());
            this.opponents.remove(game.getWhitePlayer().getName());
            this.activeGames.remove(game);
        }

    /*
     * Removes game from the game center and removes the pairing from the hashmap. This will effectively
     * delete the game from the server so it no longer exists.
     * @param gameToResignFrom the game from which the player is resigning
     * @param playerThatsResigning the player that is resigning
     */
    public synchronized void resignFromGame(Game gameToResignFrom, Player playerThatsResigning) {
        Player otherPlayer = gameToResignFrom.getOpponentOf(playerThatsResigning);
        // remove from the current active games
        activeGames.remove(gameToResignFrom);
        // this overloaded version only removes key with this value
        // TODO: will have to add more checks once multiple games are added because you can play
        // more than one game against the same player
        opponents.remove(playerThatsResigning.getName(), otherPlayer);
        opponents.remove(otherPlayer.getName(), playerThatsResigning);
    }
}
