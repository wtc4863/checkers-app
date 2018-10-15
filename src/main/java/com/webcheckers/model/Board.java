package com.webcheckers.model;

import com.webcheckers.ui.BoardView;
import com.webcheckers.ui.PieceView;
import com.webcheckers.ui.RowView;
import com.webcheckers.ui.SpaceView;

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
     * Create a new Board with spaces and pieces
     *
     */
    public Board() {

        boardArray = new Space[rows][columns];

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
        location1.removePiece();
        location2.addPiece(beingMoved);

    }

    public BoardView getBoardViewVersion() {
        ArrayList<RowView> rowViews = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            ArrayList<SpaceView> row = new ArrayList<>();
            for (int j = 0; j <= 7; j++) {
                Space space = boardArray[i][j];
                SpaceView spaceView;
                if (space.isValid()) {
                    spaceView = new SpaceView(j, null);
                }
                else {
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
                    spaceView = new SpaceView(j, pieceView);
                }
                row.add(spaceView);
            }
            RowView rowView = new RowView(i, row);
            rowViews.add(rowView);
        }
        return new BoardView(rowViews);
    }
}