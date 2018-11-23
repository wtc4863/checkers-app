package com.webcheckers.appl;

import org.eclipse.jetty.util.SocketAddressResolver;

import java.util.Objects;

/**
 * A pure fabrication object used as an interface between the Post*AsyncRoute
 * route components and the rest of the application.
 */
public class AsyncServices {

    //
    // Constants
    //

    //
    // Attributes
    //
    private final PlayerLobby playerLobby;
    private final GameCenter gameCenter;

    //
    // Constructor
    //
    public AsyncServices(final PlayerLobby playerLobby, final GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.playerLobby = playerLobby;
        this.gameCenter = gameCenter;
    }

    //
    // Methods
    //

    /**
     * Start the asynchronous play request for all the synchronous games a
     * player is currently in.
     *
     * @param sessionID
     *      The session ID of the player requesting the transition to
     *      asynchronous mode.
     */
    public void startAsync(String sessionID) {
    }
}
