package com.webcheckers.model;

import com.webcheckers.ui.BoardView;
import com.webcheckers.ui.PieceView;
import com.webcheckers.ui.RowView;
import com.webcheckers.ui.SpaceView;

import java.util.ArrayList;

/**
 * Object that holds all of the game data for the state of the board
 */

public class Board {
    //
    // Attributes
    //
    /** Number of ROWS and COLUMNS*/
    public static final int ROWS = 8;
    public static final int COLUMNS = 8;

    /** used for alternating colors(space) on the board*/
    private boolean darkSpace = true;

    /** 2D Array of spaces*/
    private Space[][] boardArray;

    //
    // Constructor
    //
    public Board() {

        boardArray = new Space[ROWS][COLUMNS];
        //start by creating all of the spaces on the board as empty spaces
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                //Even ROWS start with black space
                if (i % 2 == 0) {
                    if (j % 2 != 0) {
                        boardArray[i][j] = new Space(Space.SpColor.black);
                    } else {
                        boardArray[i][j] = new Space(Space.SpColor.white);
                    }
                } else {
                    if (j % 2 != 0) {
                        boardArray[i][j] = new Space(Space.SpColor.white);
                    } else {
                        boardArray[i][j] = new Space(Space.SpColor.black);
                    }
                }
            }
        }
        //go back through all of the spaces and put pieces where they belong
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (i <= 2) {
                    if (boardArray[i][j].isBlack()) {
                        boardArray[i][j].addPiece(new Piece(Piece.PColor.white, Piece.PType.single));
                    }
                } else if(i >= 5) {
                    if (boardArray[i][j].isBlack()) {
                        boardArray[i][j].addPiece(new Piece(Piece.PColor.red, Piece.PType.single));
                    }
                }
            }
        }


    }

    //
    // Methods
    //
    /**
     * Move piece from one location to another, if possible
     * @param location1 location the piece starts in
     * @param location2 the desired location of the piece
     */
    public void move(Space location1, Space location2) {

        Piece beingMoved = location1.pieceInfo();
        if(location2.isValid()) {
            location1.removePiece();
            location2.addPiece(beingMoved);
        }


    }

    public Space[] getRow(int rowIndex) {
        return this.boardArray[rowIndex];
    }
}