package com.webcheckers.model;

public class Space {

    /** Colors for space */
    public enum SpColor { black, white }

    /** Color of this space */
    private SpColor spacecolor;

    private boolean hasPiece;

    /** Piece that is occupying the space*/
    private Piece PieceOnSpace;

    /**
     * Create a new Space
     *
     * @param color The color of the space
     */
    public Space(SpColor color, Piece onSpace) {
        spacecolor = color;
        hasPiece = true;
        PieceOnSpace = onSpace;

    }

    public Space(SpColor color) {
        spacecolor = color;
        hasPiece = false;
    }

    public boolean isBlack() {
        return spacecolor == SpColor.black;
    }

    public boolean doesHasPiece() {
        return this.hasPiece;
    }

    /**
     * Check if the space is valid
     *
     * @return boolean
     */
    public boolean isValid(){
        return (this.spacecolor == SpColor.black && !this.hasPiece);
    }

    /**
     * Get the information of the piece on space
     *
     * @return Piece
     */
    public Piece pieceInfo(){

        if(!isValid())
            return PieceOnSpace;

        else
            return null;
    }
    /**
     * Remove a piece from a Space
     *
     * @param pieceAdd Piece to be added to the space
     */
    public void addPiece(Piece pieceAdd){
        if(isValid()) {
            this.PieceOnSpace = pieceAdd;
            this.hasPiece = true;
        }
        else {

        }
    }
    /**
     * Remove a piece from a Space
     *
     */
    public void removePiece(){
        if(doesHasPiece()) {
            this.hasPiece = false;
            this.PieceOnSpace = null;
        }
        else {

        }
    }
}