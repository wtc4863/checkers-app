package com.webcheckers.model;

public class Space {

    /** Colors for space */
    public enum SpColor { black, white }

    /** Color of this space */
    private SpColor spacecolor;

    /** Space is taken or not  */
    private boolean valid;

    /** Piece that is occupying the space*/
    private Piece PieceOnSpace;

    /**
     * Create a new Space
     *
     * @param color The color of the space
     */
    public Space(SpColor color, Piece onSpace) {
        spacecolor = color;
        valid = false;
        PieceOnSpace = onSpace;

    }

    public Space(SpColor color) {
        spacecolor = color;
        valid = true;
    }

    public boolean isBlack() {
        return spacecolor == SpColor.black;
    }

    /**
     * Check if the space is valid
     *
     * @return boolean
     */
    public boolean isValid(){
        return this.valid;
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
            this.valid = false;
            this.PieceOnSpace = pieceAdd;
        }
        else {

        }
    }
    /**
     * Remove a piece from a Space
     *
     */
    public void removePiece(){
        if(isValid()) {
            this.valid = true;
            this.PieceOnSpace = null;
        }
        else {

        }
    }
}