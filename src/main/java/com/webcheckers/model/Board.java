package com.webcheckers.model;

import com.webcheckers.ui.BoardView;

public class Board {

    /** Number of rows and columns*/
    private static int rows = 8;
    private static int columns = 8;

    /** used for alternating colors(space) on the board*/
    private boolean darkSpace = true;

    /** 2D Array of spaces*/
    private Space[][] boardArray;

    /**
     * Create a new Board with spaces and pieces
     *
     */
    public Board() {

        boardArray = new Space[rows][columns];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){

                //black and bottom 2
                if (darkSpace == true && i<3){
                    boardArray[i][j] = new Space(Space.SpColor.black, new Piece(Piece.PColor.red));
                    darkSpace = false;
                }

                // black space and top 2 rows
                else if (darkSpace == true && i>5){
                    boardArray[i][j] = new Space(Space.SpColor.black, new Piece(Piece.PColor.white));
                    darkSpace = false;
                }

                //black space and no pieces
                else if (darkSpace == true && i<6 && i>2){
                    boardArray[i][j] = new Space(Space.SpColor.black, null);
                    darkSpace = false;
                }

                // white space
                else {
                    boardArray[i][j] = new Space(Space.SpColor.white, null);
                    darkSpace =true;
                }
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