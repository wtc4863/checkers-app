package com.webcheckers.appl;
import com.webcheckers.model.Player;
import java.util.HashMap;

public class PlayerLobby {

    private HashMap<String, Player> players;
    private GameCenter gameCenter;

    public PlayerLobby() {
        this.players = new HashMap<>();
        this.gameCenter = new GameCenter();
    }

}