package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.ui.BoardView;

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
    private static final String ALPHANUMERIC_REGEX = "^.*[A-Za-z0-9]+.*$";
    private static final String VALID_NAME_REGEX = "^[A-Za-z0-9 ]*$";

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
        this(new GameCenter());
    }

    /**
     * Create a new PlayerLobby instance with a given GameCenter object.
     * Currently used only for testing purposes.
     *
     * @param gameCenter
     *      the GameCenter object to use when creating the new
     *      PlayerLobby instance.
     */
    PlayerLobby(GameCenter gameCenter) {
        this.players = new HashMap<>();
        this.gameCenter = gameCenter;
    }

    //
    // Methods
    //

    public Player getPlayer(String name) {
        return players.get(name);
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
        return signIn(new Player(playerName, sessionID));
    }

    /**
     * Sign in a player.
     *
     * @param player the Player object to use to sign in the player
     * @return
     *      true if the player was signed in successfully, false if the player
     *      could not be signed in.
     */
    public synchronized boolean signIn(Player player) {
        if(!validName(player.getName())) {
            return false;
        } else if (getSignedInPlayers().contains(player.getName())) {
            return false;
        } else {
            players.put(player.getName(), player);
            return true;
        }
    }

    public void signOut(String playerName) {
        players.get(playerName).signOut();
        players.remove(playerName);
    }

    /**
     * Checks if the given sessionID is already registered to a player.
     * @param sessionID the session id to check
     * @return player name if a player is using the given ID
     */
    public String getPlayerNameBySessionID(String sessionID) {
        for(Player player: players.values()) {
            if(player.isSignedIn(sessionID))
                return player.getName();
        }
        return null;
    }

    /**
     * Retrieves a player based on the session ID associated with the player.
     * @param sessionID the session ID associated with the player
     * @return the Player object associated with the session ID, or Null if
     * there is no player for the given session ID.
     */
    public Player getPlayerBySessionID(String sessionID) {
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

    /**
     * Passes up the opponent of a specific player from GameCenter
     * @param player player whose opponent is being found
     * @return player object of opponent
     */
    public Player getOpponent(Player player) {
       return this.gameCenter.getOpponent(player);
    }

    /**
     * Passes up the game object a specific player is playing in
     * @param player player whose game is being found
     * @return game object of player, null if player is not in game
     */
    public Game getGame(Player player) {
        if (player == null) {
            return null;
        } else {
            return this.gameCenter.getGame(player);
        }
    }

    /**
     * Tells the GameCenter to create a new game with two players
     * @param redPlayer player on the red team
     * @param whitePlayer player on the white team
     * @return Game object of these two players
     */
    public Game startGame(Player redPlayer, Player whitePlayer) {
        return gameCenter.startGame(redPlayer, whitePlayer);
    }

    /**
     * Tells the GameCenter to get the BoardView for a specific player
     * @param player player that the BoardView is for
     * @return BoardView of this player
     */
    public BoardView getBoardView(Player player) {
        return gameCenter.getBoardView(player);
    }
}