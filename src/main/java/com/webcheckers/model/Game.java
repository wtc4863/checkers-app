package com.webcheckers.model;

public class Game {
    private Player redPlayer;
    private Player whitePlayer;
    private Board board;

    public Game(Player redPlayer, Player whitePlayer) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
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
}
