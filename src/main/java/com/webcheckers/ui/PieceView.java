package com.webcheckers.ui;

public class PieceView {
    enum color {
        RED, WHITE;
    }

    enum type {
        SINGLE, KING;
    }

    private color color;
    private type type;

    public type getType() {
        return type;
    }

    public color getColor() {
        return this.color;
    }
}
