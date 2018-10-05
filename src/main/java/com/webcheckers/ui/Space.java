package com.webcheckers.ui;

public class Space {

    private Piece piece;
    private int cellIdx;

    public Space(int cellIdx, Piece piece) {
        this.cellIdx = cellIdx;
        this.piece = piece;
    }

    public int getCellIdx() {
        return this.cellIdx;
    }

    public boolean isValid() {
        if (this.piece == null) {
            return true;
        }
        return false;
    }

    public Piece getPiece() {
        return piece;
    }

}
