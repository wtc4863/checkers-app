package com.webcheckers.appl;

import com.webcheckers.model.*;
import com.webcheckers.ui.BoardView;

import java.util.ArrayList;
import java.util.HashMap;

public class GameCenter {

    private static ArrayList<Game> activeGames;
    private static HashMap<String, Player> opponents;

    public GameCenter() {
        this.activeGames = new ArrayList<>();
        this.opponents = new HashMap<>();
    }

    public Player getOpponent(Player player) {
        return opponents.get(player.getName());
    }

    public Game getGame(Player player) {
        for (Game game : activeGames) {
            if (player.equals(game.getRedPlayer()) || player.equals(game.getWhitePlayer())) {
                return game;
            }
        }
        return null;
    }

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
}
