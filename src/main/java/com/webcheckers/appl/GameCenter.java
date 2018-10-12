package com.webcheckers.appl;

import com.webcheckers.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class GameCenter {

    private static ArrayList<Game> activeGames;
    private static HashMap<String, Player> opponents;

    public GameCenter() {
        this.activeGames = new ArrayList<>();
        this.opponents = new HashMap<>();
    }

    public static Player getOpponent(Player player) {
        return opponents.get(player.getName());
    }

    public static Game getGame(Player player) {
        for (Game game : activeGames) {
            if (player.equals(game.getRedPlayer()) || player.equals(game.getWhitePlayer())) {
                return game;
            }
        }
        return null;
    }
}
