package com.webcheckers.model;

public class Space {

    //
    // Attributes
    //
    public enum SpColor { black, white }
    private SpColor spacecolor;
    private boolean hasPiece;
    private Piece PieceOnSpace;

    //
    // Constructors
    //
    public Space(SpColor color, Piece onSpace) {
        spacecolor = color;
        hasPiece = true;
        PieceOnSpace = onSpace;
    }

    public Space(SpColor color) {
        spacecolor = color;
        hasPiece = false;
    }

    //
    // Methods
    //

    /**
     * Tells if this Space is black or not
     * @return true if black
     */
    public boolean isBlack() {
        return spacecolor == SpColor.black;
    }

    /**
     * Tells if a piece is on this space
     * @return true if space has a piece
     */
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
     * Remove a piece from a Space, if possible
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