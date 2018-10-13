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

    public BoardView getBoardView() {
        return this.board.getBoardViewVersion();
    }

    public Turn getTurn() {
        return this.turn;
    }
}
