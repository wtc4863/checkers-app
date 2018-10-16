package com.webcheckers.model;

import com.webcheckers.ui.BoardView;

/**
 * Object that holds all of the data for a specific game
 */

public class Game {
    //
    // Attributes
    //
    private Player redPlayer;
    private Player whitePlayer;
    private Board board;
    private Turn turn;

    enum Turn {
        WHITE, RED;
    }

    //
    // Constructor
    //
    public Game(Player redPlayer, Player whitePlayer) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.turn = Turn.RED;
        this.board = new Board();
    }

    //
    // Methods
    //

    /**
     * Gets the red player in this Game
     * @return player object of red player
     */
    public Player getRedPlayer() {
        return this.redPlayer;
    }

    /**
     * Gets the white player in this Game
     * @return player object of white player
     */
    public Player getWhitePlayer() {
        return this.whitePlayer;
    }

    /**
     * Gets the board state of this Game
     * @return board object of this Game
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Passes up the BoardView of the current Game
     * @param opposite if true, render white pieces at the bottom of the board
     * @return BoardView object
     */
    public BoardView getBoardView(boolean opposite) {
        return this.board.getBoardViewVersion(opposite);
    }

    /**
     * Gets the color of the current turn
     * @return red or white enum
     */
    public Turn getTurn() {
        return this.turn;
    }
}
