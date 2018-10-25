package com.webcheckers.appl;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the {@link PlayerLobby} component.
 */
@Tag("Application-Tier")
public class PlayerLobbyTest {

    /**
     * Test that getPlayer will return null when the passed player does not
     * exist.
     */
    @Test
    public void getPlayer_should_returnNull_when_playerDoesNotExist() {
        // TODO: A non-existent player returns null
    }

    /**
     * Test that getPlayer will return a Player object when the passed player
     * has signed in.
     */
    @Test
    public void getPlayer_should_returnPlayer_when_playerSignedIn() {
        // TODO: A signed-in player returns a Player object
    }

    /**
     * Test that getSignedInPlayers will not include in the list a player who
     * has signed out.
     */
    @Test
    public void getSignedInPlayers_should_notIncludePlayer_when_signedOut() {
        // TODO: A player who has signed out will not be included
    }

    /**
     * Test that getSignedInPlayers will include in the list a player who is
     * signed in.
     */
    @Test
    public void getSignedInPlayers_should_includePlayer_when_signedIn() {
        // TODO: A player who has signed in will be included
    }

    /**
     * Test that getSignedInPlayers will return an empty list if there are no
     * players.
     */
    @Test
    public void getSignedInPlayers_should_emptyList_when_noPlayersExist() {
        // TODO: An empty list will be returned if no players exist
    }

    /**
     * Test that an empty username should not be allowed to sign in.
     */
    @Test
    public void signIn_should_fail_when_emptyPlayerName() {
        // TODO: An empty name returns false
    }

    /**
     * Test that an all-space username should not be allowed to sign in.
     */
    @Test
    public void signIn_should_fail_when_allSpaceName() {
        // TODO: An all-space name returns false
    }

    /**
     * Test that a player who has already signed in should not be allowed to
     * sign in.
     */
    @Test
    public void signIn_should_fail_when_alreadySignedIn() {
        // TODO: A player that is already signed in returns false
    }

    /**
     * Test that a player name that is valid which is not signed in should be
     * allowed to sign in.
     */
    @Test
    public void signIn_should_pass_when_validNameNotSignedIn() {
        // TODO: A player that is not signed in and has a valid name returns true
    }

    /**
     * Test that getPlayerNameBySessionID returns null when a session ID is not
     * associated with a player.
     */
    @Test
    public void playerName_should_returnNull_when_sessionIDNotInUse() {
        // TODO: A session ID not associated with a player returns null
    }

    /**
     * Test that getPlayerNameBySessionID returns the name of the player who is
     * signed in with that session ID.
     */
    @Test
    public void playerName_should_returnNameString_when_sessionIDInUse() {
        // TODO: A session ID for a signed-in player returns the player name
    }

    /**
     * Test that getPlayerBySessionID returns null when a session ID is not
     * associated with a player.
     */
    @Test
    public void player_should_returnNull_when_SessionIDNotInUse() {
        // TODO: A session ID not associated with a player returns null
    }

    /**
     * Test that getPlayerBySessionID returns the player object that is signed
     * in with that session ID.
     */
    @Test
    public void player_should_returnPlayerObject_when_sessionIDNotInUse() {
        // TODO: A session ID for a signed-in player returns the player
    }
}