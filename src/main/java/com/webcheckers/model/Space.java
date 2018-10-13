package com.webcheckers.model;

public class Space {

    /** Colors for space */
    public enum Color { black, white }

    /** Color of this space */
    private Color spacecolor;

    /** Space is taken or not  */
    private boolean valid;

    /** Piece that is occupying the space*/
    private Piece PieceOnSpace;

    /**
     * Check if the space is valid
     *
     * @param color The color of the space
     */
    public Space(Color color) {
        spacecolor = color;
        valid = true;

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

        if(isValid())
            return PieceOnSpace;

        else
            return null;
    }
}