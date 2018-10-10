package com.webcheckers.model;

import com.webcheckers.ui.PlayerView;

/**
 * TODO
 */
public class Player {
    private String sessionID;
    private final String name;

    /**
     * Create a new signed-out player
     *
     * @param name The name of the new player
     */
    public Player(String name) {
        this.name = name;
        this.sessionID = null;
    }

    /**
     * Create a new signed-in player
     *
     * @param name      The name of the new player
     * @param sessionID The session ID to associate with the player
     */
    public Player(String name, String sessionID) {
        this.name = name;
        this.sessionID = sessionID;
    }

    /**
     * Get the player's name
     *
     * @return The player's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Checks if a player is signed in with a given session id
     * @param sessionID the sessionID to check against
     * @return true if the sessinonID matches this players sessionID
     */
    public boolean isSignedIn(String sessionID) {
        return this.sessionID.equals(sessionID);
    }

    /**
     * Check if the player is currently signed in
     *
     * @return {@code true} if the player is signed in, {@code false} if the
     * player is not signed in
     */
    public boolean isSignedIn() {
        return this.sessionID != null;
    }

    /**
     * Sign in the player and associate a given session ID with the player
     *
     * @param sessionID The session ID to associate with the player
     */
    public void signIn(String sessionID) {
        this.sessionID = sessionID;
    }

    /**
     * Sign out the player.
     */
    public void signOut() {
        this.sessionID = null;
    }

    /**
     * Construct a PlayerView representation of this player for use by
     * templates
     * @return a PlayerView object that represents this player
     */
    public PlayerView getPlayerView() {
        return new PlayerView(this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Player)) return false;
        final Player that = (Player) obj;
        return this.name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
