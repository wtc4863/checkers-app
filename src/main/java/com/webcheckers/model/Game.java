package com.webcheckers.model;

import com.webcheckers.ui.BoardView;

import java.util.ArrayList;
import java.util.Queue;

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
    private ArrayList<Move> queuedTurnMoves;

    public enum Turn {
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
        this.queuedTurnMoves = new ArrayList<>();
    }

    // used for custom configuration
    public Game(Player redPlayer, Player whitePlayer, Turn turn, Board board) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.turn = turn;
        this.board = board;
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
        return new BoardView(this.board, opposite);
    }

    /**
     * Gets the color of the current turn
     * @return red or white enum
     */
    public Turn getTurn() {
        return this.turn;
    }

    public void switchTurn() {
        if(this.turn == Turn.RED)
            this.turn = Turn.WHITE;
        else
            this.turn = Turn.RED;
    }

    /**
     * Applies the current players moves to the board, changes the turn to the other player
     */
    public void applyTurnMoves() {
        for (Move move : queuedTurnMoves) {
            move.executeMove(this);
        }
        switchTurn();
        queuedTurnMoves.clear();
    }

    /**
     * Checks if there are moves left to be made in this turn
     * @return true if there are moves left to be made in this turn
     */
    public boolean movesLeft() {
        //if there is a move left, the most recent move's end position is the next move's start position
        Move lastMove = queuedTurnMoves.get(queuedTurnMoves.size() -1 );
        Position newStart = lastMove.getEnd();
        return JumpMove.positionHasJumpMoveAvailable(newStart, this);
    }

    public void addMove(Move move) {
        queuedTurnMoves.add(move);
    }
}
