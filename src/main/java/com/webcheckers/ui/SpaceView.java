package com.webcheckers.ui;

public class SpaceView {

    private PieceView pieceView;
    private int cellIdx;

    public SpaceView(int cellIdx, PieceView pieceView) {
        this.cellIdx = cellIdx;
        this.pieceView = pieceView;
    }

    public int getCellIdx() {
        return this.cellIdx;
    }

    public boolean isValid() {
        if (this.pieceView == null) {
            return true;
        }
        return false;
    }

    public PieceView getPieceView() {
        return pieceView;
    }

}
