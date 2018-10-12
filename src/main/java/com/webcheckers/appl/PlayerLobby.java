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
    public static final String ALPHANUMERIC_REGEX = "^.*[A-Za-z0-9]+.*$";
    public static final String VALID_NAME_REGEX = "^[A-Za-z0-9 ]*$";

    //
    // Attributes
    //

    /**
     * Record of all players that have ever signed into the game
     */
    private static HashMap<String, Player> players;

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

    public static Player getPlayer(String name) {
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
     * Helper method that checks if a player's name is valid
     *
     * @param playerName the player name to check
     * @return true if the name is valid, false if invalid
     */
    private boolean validName(String playerName) {
        return playerName.matches(ALPHANUMERIC_REGEX) && playerName.matches(VALID_NAME_REGEX);
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

    /**
     * Sign in a player.
     *
     * @param playerName the username of the player to sign in as
     * @param sessionID the session ID to associate with the player
     * @return
     *      true if the player was signed in successfully, false if the player
     *      could not be signed in.
     */
    public synchronized boolean signIn(String playerName, String sessionID) {
        if (!validName(playerName)) {
            return false;
        } else if (getSignedInPlayers().contains(playerName)) {
            return false;
        } else {
            players.put(playerName, new Player(playerName, sessionID));
            return true;
        }
    }

    /**
     * Checks if the given sessionID is already registered to a player.
     * @param sessionID the session id to check
     * @return player name if a player is using the given ID
     */
    public String isAlreadySignedIn(String sessionID) {
        for(Player player: players.values()) {
            if(player.isSignedIn(sessionID))
                return player.getName();
        }
        return null;
    }

    /**
     * Return the number of online players
     * @return an int representing the number of online players
     */
    public int getNumberOnlinePlayers() {
        return getSignedInPlayers().size();
    }

    /**
     * Retrieves a player based on the session ID associated with the player.
     * @param sessionID the session ID associated with the player
     * @return the Player object associated with the session ID, or Null if
     * there is no player for the given session ID.
     */
    public Player getBySessionID(String sessionID) {
        for(Player player : players.values()) {
            if(player.isSignedIn(sessionID)) {
                // If we've found a match, we can return now.  There should be
                // no session ID duplicates.  If there is, either something
                // else is broken in our code, or we just found a CVE in Spark.
                return player;
            }
        }
        // If we get here, then there are no players that match that session ID
        return null;
    }
}