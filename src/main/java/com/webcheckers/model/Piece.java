package com.webcheckers.model;

public class Piece {

    /** Colors for Piece */
    public enum PColor { red, white }

    public PColor pieceColor;

    public boolean King;


    /**
     * Create a new Piece
     *
     * @param pCol The color of the piece
     */
    public Piece(PColor pCol){
        pieceColor = pCol;
        King = false;
    }

    /**
     * Check if the piece is king
     *
     * @return boolean
     */
    public boolean isKing() {
        return King;
    }
}
