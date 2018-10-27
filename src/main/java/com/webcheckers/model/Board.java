package com.webcheckers.model;


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
    Space[][] boardArray;

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
     * Move piece from one location to another, not worrying
     * about validation. We assume the move calling this
     * has already validated
     * @param location1 location the piece starts in
     * @param location2 the desired location of the piece
     */
    public void move(Position location1, Position location2) {
        Space startSpace = boardArray[location1.getRow()][location1.getCol()];
        Space endSpace = boardArray[location2.getRow()][location2.getCol()];
        Piece beingMoved = startSpace.pieceInfo();
            startSpace.removePiece();
            endSpace.addPiece(beingMoved);
    }

    public boolean spaceIsValid(Position position) {
        Space space = boardArray[position.getRow()][position.getCol()];
        return space.isValid();
    }

    /**
     * Return a row of spaces at the specified index
     * @param rowIndex the index of the row to return
     * @return a row of spaces
     */
    public Space[] getRow(int rowIndex) {
        return this.boardArray[rowIndex];
    }

    public Space getSpace(int rowIndex, int colIndex) {
        return boardArray[rowIndex][colIndex];
    }
}