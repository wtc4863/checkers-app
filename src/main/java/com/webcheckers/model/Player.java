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
     * @param name
     *      The name of the new player
     */
    public Player(String name) {
        this.name = name;
        this.sessionID = null;
    }

    /**
     * Create a new signed-in player
     *
     * @param name
     *      The name of the new player
     * @param sessionID
     *      The session ID to associate with the player
     */
    public Player(String name, String sessionID) {
        this.name = name;
        this.sessionID = sessionID;
    }

    /**
     * Get the player's name
     *
     * @return
     *      The player's name
     */
    public String getName() {
        return this.name;
    }

    public boolean isSignedIn() {
        return false;
    }

    public void signIn(String sessionID) {

    }

    public void signOut() {

    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
