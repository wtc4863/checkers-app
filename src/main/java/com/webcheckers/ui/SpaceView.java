package com.webcheckers.ui;

import com.webcheckers.model.Space;

import javax.swing.text.View;

/**
 * The class that represents the UI tier of a space
 */

public class SpaceView {
    //
    // Attributes
    //
    private PieceView pieceView;
    private int cellIdx;
    private ViewColor viewColor;

    /**
     * A SpaceView either represents a white or black space
     */
    public enum ViewColor {
        WHITE, BLACK;
    }

    //
    // Constructor
    //
    public SpaceView(int cellIdx, PieceView pieceView, boolean isBlack) {  // TODO remove old constructor
        this.cellIdx = cellIdx;
        this.pieceView = pieceView;
        if(isBlack) {
            this.viewColor = ViewColor.BLACK;
        } else {
            this.viewColor = ViewColor.WHITE;
        }
    }

    /**
     * Create a new SpaceView object using a model-tier representation of the
     * space.
     *
     * @param space the model-tier representation of a space
     * @param cellIndex the index of the space in the row
     */
    public SpaceView(Space space, int cellIndex) {
        this.cellIdx = cellIndex;
        this.pieceView = null;

        if (space.isBlack()) {
            this.viewColor = ViewColor.BLACK;
        } else {
            this.viewColor = ViewColor.WHITE;
        }

        if (space.doesHasPiece()) {
            this.pieceView = new PieceView(space.pieceInfo());
        }
    }

    //
    // Methods
    //
    /**
     * Gets the index of the space in its row
     * @return index of cell
     */
    public int getCellIdx() {
        return this.cellIdx;
    }

    /**
     * Determines if the space this object represents can have a piece placed on it
     * @return true if the space is black and there is no piece already on it
     */
    public boolean isValid() {
        return this.pieceView == null && this.viewColor == ViewColor.BLACK;
    }

    /**
     * Gets the UI object that represents the piece on the space this object represents
     * @return PieceView of this space's piece
     */
    public PieceView getPieceView() {
        return pieceView;
    }

}
