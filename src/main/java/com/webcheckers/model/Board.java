package com.webcheckers.model;

import com.webcheckers.ui.BoardView;

public class Board {

    /** Number of rows and columns*/
    private static int rows = 8;
    private static int columns = 8;

    /** 2D Array of spaces*/
    private Space[][] boardArray;

    public Board() {

        boardArray = new Space[rows][columns];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){

            }
        }

    }

    public BoardView getBoardViewVersion() {
        return null;
    }
}
