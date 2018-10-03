package com.webcheckers.model;

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
