package com.webcheckers.model;

import com.webcheckers.ui.BoardView;

public class Game {
    private Player redPlayer;
    private Player whitePlayer;
    private Board board;
    private Turn turn;

    enum Turn {
        WHITE, RED;
    }

    public Game(Player redPlayer, Player whitePlayer) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.turn = Turn.RED;
        this.board = new Board();
    }

    public Player getRedPlayer() {
        return this.redPlayer;
    }

    public Player getWhitePlayer() {
        return this.whitePlayer;
    }

    public Board getBoard() {
        return this.board;
    }

    public BoardView getBoardView(boolean opposite) {
        return this.board.getBoardViewVersion(opposite);
    }

    public Turn getTurn() {
        return this.turn;
    }
}
