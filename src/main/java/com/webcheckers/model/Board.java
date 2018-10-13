package com.webcheckers.model;

import com.webcheckers.ui.BoardView;

public class Board {

    /** Number of rows and columns*/
    private static int rows = 8;
    private static int columns = 8;

    /** 2D Array of spaces*/
    private Space[][] boardArray;

    /**
     * TODO (Alternating colors for spaces)
     * Create a new Board
     *
     */
    public Board() {

        boardArray = new Space[rows][columns];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                boardArray[i][j] = new Space(Space.SpColor.black, null);
            }
        }

    }

    /**
     * Move piece from one location to the other 
     *
     */
    public void move(Space location1, Space location2) {

        Piece beingMoved = location1.pieceInfo();
        location1.removePiece();
        location2.addPiece(beingMoved);

    }

    public BoardView getBoardViewVersion() {
        return null;
    }
}
