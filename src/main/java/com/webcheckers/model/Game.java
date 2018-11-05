package com.webcheckers.model;

import com.webcheckers.ui.BoardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Queue;
import java.util.logging.Logger;

/**
 * Object that holds all of the data for a specific game
 */

public class Game {
    private static Logger LOG = Logger.getLogger(Game.class.getName());
    //
    // Attributes
    //

    Player redPlayer;
    Player whitePlayer;
    Board board;
    Turn turn;
    ArrayList<Move> queuedTurnMoves;

    public enum Turn {
        WHITE, RED;
    }

    //
    // Constructor
    //
    public Game(Player redPlayer, Player whitePlayer) {
        LOG.fine(String.format("Game created: (%s : %s", redPlayer, whitePlayer));
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.turn = Turn.RED;
        this.board = new Board();
        this.queuedTurnMoves = new ArrayList<>();
    }

    // used for custom configuration
    public Game(Player redPlayer, Player whitePlayer, Turn turn, Board board) {
        LOG.fine(String.format("Custom Game created: (%s : %s", redPlayer, whitePlayer));
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.turn = turn;
        this.board = board;
        this.queuedTurnMoves = new ArrayList<>();
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
     * Finds out of the supplied players is the player
     * whos turn it is
     * @return player object of white player
     */
    public boolean isPlayersTurn(Player player) {
        if(redPlayer.equals(player)) {
            return turn == Turn.RED;
        } else {
            return turn == Turn.WHITE;
        }
    }

    /**
     * Returns the number of moves in the current turn
     * @return true if there have been moves already made
     */
    public boolean hasMovesInCurrentTurn() {
        return queuedTurnMoves.size() >= 1;
    }

    public Move getLastMoveMade() {
        if(hasMovesInCurrentTurn()) {
            int index = queuedTurnMoves.size() - 1;
            return queuedTurnMoves.get(index);
        }
        return null;
    }

    public void addMoveToCurrentTurn(Move newest) {
        queuedTurnMoves.add(newest);
        LOG.fine(String.format("Move added to current turn: %d", queuedTurnMoves.size()));
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
        switch(this.turn){
            case RED:
                this.turn = Turn.WHITE;
                break;
            case WHITE:
                this.turn = Turn.RED;
                break;
        }
        LOG.fine(this.turn.toString() + "'s Turn");
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
        if(queuedTurnMoves.size() > 0) {
            Move lastMove = queuedTurnMoves.get(queuedTurnMoves.size() - 1);
            // if a simple move is made it can be on only move
            if(lastMove instanceof SimpleMove) return false;
            Position newStart = lastMove.getEnd();
            return JumpMove.positionHasJumpMoveAvailable(newStart, this);
        }
        return false;
    }

    public void addMove(Move move) {
        queuedTurnMoves.add(move);
    }

    public Move removeMove() {
        int size = queuedTurnMoves.size();
        if(size > 0) {
            Move move = queuedTurnMoves.get(size - 1);
            queuedTurnMoves.remove(size - 1);
            return move;
        }
        return null;
    }
}
