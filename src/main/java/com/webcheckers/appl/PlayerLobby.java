package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * The Application-tier controller that is responsible for all Player-based
 * operations, such as signing in and out from the game.
 *
 * @author <a href="mailto:rxc3202@rit.edu>Ryan Cervantes</a>
 */
public class PlayerLobby {

    //
    // Constants
    //
    public static final String ALPHANUMERIC_REGEX = "^[A-Za-z0-9]+$";
    public static final String VALID_NAME_REGEX = "^[A-Za-z0-9 ]*$";

    //
    // Attributes
    //

    /**
     * Record of all players that have ever signed into the game
     */
    private HashMap<String, Player> players;

    /**
     * The GameCenter instance used to handle game information
     */
    private GameCenter gameCenter;

    //
    // Constructors
    //

    /**
     * Create a new PlayerLobby instance
     */
    public PlayerLobby() {
        this.players = new HashMap<>();
        this.gameCenter = new GameCenter();
    }

    //
    // Methods
    //

    private Player getPlayer(String name) {
        return players.get(name);
    }

    /**
     * Get the names of all players that have ever signed into the game
     *
     * @return a set of all player names
     */
    private Set<String> getPlayerNames() {
        return players.keySet();
    }

    /**
     * This method will check if the given name exists. If the name exists
     * there is a player that already has that name since all names must be
     * unique
     *
     * @param name the name to check
     * @return true if player in current instance of the web server
     */
    private boolean doesPlayerExist(String name) {
        return !getPlayerNames().contains(name);
    }

    /**
     * This method will allow the model to create a new player instance
     * and reserve it's name so that it cannot be used. The new player will
     * also be added to the current list of available players
     *
     * @param possibleName the name that the user wants to use
     * @return true if the user was successfully created
     */
    //TODO: Must implement sessionID
    public synchronized boolean registerNewPlayer(String possibleName) {
        if (!doesPlayerExist(possibleName)) return false;
        Player p = new Player(possibleName);
        players.put(possibleName, p);
        return true;
    }

    /**
     * This method will sign in an existing player with a given session ID
     *
     * @param playerName the name of the player
     * @param sessionID  the unique current sessionID of the player
     * @return true if the sessionID is updated
     */
    public synchronized boolean signInExistingPlayer(String playerName, String sessionID) {
        Player existingPlayer = getPlayer(playerName);
        existingPlayer.signIn(sessionID);
        return true;
    }

    /**
     * This function returns a list of names of the players that are currently
     * signed in to the webapp
     *
     * @return an ArrayList of Strings that are unique values for each signed in player
     */
    public synchronized ArrayList<String> getSignedInPlayers() {
        ArrayList<String> signedInPlayers = new ArrayList<>();
        for (Player player : players.values()) {
            if (player.isSignedIn())
                signedInPlayers.add(player.getName());
        }
        return signedInPlayers;
    }
}