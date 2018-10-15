package com.webcheckers.model;

public class Piece {

    /** Colors for Piece */
    public enum PColor { red, white }

    public enum PType { single, king }

    public PColor pieceColor;

    public PType pieceType;


    /**
     * Create a new Piece
     *
     * @param pCol The color of the piece
     */
    public Piece(PColor pCol, PType pType){
        pieceColor = pCol;
        pieceType = pType;
    }

    public boolean isRed() {
        return pieceColor == PColor.red;
    }

    public boolean isKing() {
        return pieceType == PType.king;
    }

    /**
     * Check if the piece is king
     *
     * @return boolean
     */
    public PType getPieceType() {
        return pieceType;
    }
}
