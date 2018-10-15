package com.webcheckers.model;

import com.webcheckers.ui.BoardView;
import com.webcheckers.ui.PieceView;
import com.webcheckers.ui.RowView;
import com.webcheckers.ui.SpaceView;

import com.webcheckers.ui.SpaceView.ViewColor;
import java.util.ArrayList;

public class Board {

    /** Number of rows and columns*/
    private static int rows = 8;
    private static int columns = 8;

    /** used for alternating colors(space) on the board*/
    private boolean darkSpace = true;

    /** 2D Array of spaces*/
    private Space[][] boardArray;

    /**
     * Create a new Board with spaces and pieces in starting formation
     *
     */
    public Board() {

        boardArray = new Space[rows][columns];
        //start by creating all of the spaces on the board as empty spaces
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                //Even rows start with black space
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
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
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


    /**
     * Move piece from one location to the other
     *
     */
    public void move(Space location1, Space location2) {

        Piece beingMoved = location1.pieceInfo();
        if(location2.isValid()) {
            location1.removePiece();
            location2.addPiece(beingMoved);
        }


    }

    /**
     * Gets the board view for the current state of the board
     * @param opposite if the player rendering the page is white color, flip the board view
     * @return board view object
     */
    public BoardView getBoardViewVersion(boolean opposite) {
        //populate an ArrayList of RowViews to create the BoardView
        ArrayList<RowView> rowViews = new ArrayList<>();

        //for each row of the board
        for (int rowIDX = 0; rowIDX <= 7; rowIDX++) {

            ///populate an ArrayList of SpaceViews to create the RowView
            ArrayList<SpaceView> row = new ArrayList<>();
            for (int colIDX = 0; colIDX <= 7; colIDX++) {
                int i = rowIDX;
                int j = colIDX;
                if(opposite) {
                    j = (7 - j);
                    i = (7 - i);
                }
                Space space = boardArray[i][j];
                SpaceView spaceView;
                //if a space is empty, no need to make a PieceView
                if (!space.doesHasPiece()) {
                    if (space.isBlack()) {
                        spaceView = new SpaceView(j, null, true);
                    } else {
                        spaceView = new SpaceView(j, null, false);
                    }
                }else {
                    //this space has a piece on it, so create a PieceView and store it in this SpaceView
                    Piece piece = space.pieceInfo();
                    PieceView pieceView;
                    if (piece.isRed()) {
                        if (!piece.isKing()) {
                            pieceView = new PieceView(PieceView.Color.RED, PieceView.Type.SINGLE);
                        }
                        else {
                            pieceView = new PieceView(PieceView.Color.RED, PieceView.Type.KING);
                        }
                    }
                    else {
                        if (!piece.isKing()) {
                            pieceView = new PieceView(PieceView.Color.WHITE, PieceView.Type.SINGLE);
                        }
                        else {
                            pieceView = new PieceView(PieceView.Color.WHITE, PieceView.Type.KING);
                        }
                    }
                    if (space.isBlack()) {
                        spaceView = new SpaceView(j, pieceView, true);
                    } else {
                        spaceView = new SpaceView(j, pieceView, false);

                    }
                }
                row.add(spaceView);
            }
            RowView rowView = new RowView(rowIDX, row);
            rowViews.add(rowView);
        }
        return new BoardView(rowViews);
    }
}