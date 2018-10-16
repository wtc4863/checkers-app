package com.webcheckers.ui;

public class PieceView {
    private Color color;
    private Type type;

    /**
     * Piece is either red or white
     */
    public enum Color {
        RED, WHITE;
    }

    /**
     * Piece is either a single piece or a king
     */
    public enum Type {
        SINGLE, KING;
    }

    /**
     * Creates a new PieceView
     * @param color color of the piece, red or white
     * @param type type of the piece, single or king
     */
    public PieceView(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    /**
     * Gets this piece's type
     * @return type of piece
     */
    public Type getType() {
        return type;
    }

    /**
     * get the color of this piece
     * @return
     */
    public Color getColor() {
        return this.color;
    }


}
