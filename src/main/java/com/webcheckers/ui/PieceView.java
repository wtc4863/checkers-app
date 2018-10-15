package com.webcheckers.ui;

public class PieceView {
    public enum Color {
        RED, WHITE;
    }

    public enum Type {
        SINGLE, KING;
    }

    private Color color;
    private Type type;

    public PieceView(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Color getColor() {
        return this.color;
    }


}
