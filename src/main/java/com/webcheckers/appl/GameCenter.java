package com.webcheckers.appl;

import com.webcheckers.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class GameCenter {

    private ArrayList<Game> activeGames;
    private HashMap<String, Player> opponents;

    public GameCenter() {
        this.activeGames = new ArrayList<>();
        this.opponents = new HashMap<>();
    }
}
