package com.webcheckers.model;

public class Piece {

    //
    // Attributes
    //
    public enum PColor { red, white }
    public enum PType { single, king }
    public PColor pieceColor;
    public PType pieceType;


    //
    // Constructor
    //
    public Piece(PColor pCol, PType pType){
        pieceColor = pCol;
        pieceType = pType;
    }

    //
    // Methods
    //

    /**
     * Tells whether this piece is red or not
     * @return true if red
     */
    public boolean isRed() {
        return pieceColor == PColor.red;
    }

    /**
     * Tells whether this piece is a king or not
     * @return true if king piece
     */
    public boolean isKing() {
        return pieceType == PType.king;
    }

}
