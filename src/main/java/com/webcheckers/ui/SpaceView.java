package com.webcheckers.ui;

public class SpaceView {

    private PieceView pieceView;
    private int cellIdx;
    private ViewColor viewColor;

    public enum ViewColor {
        WHITE, BLACK;
    }

    public SpaceView(int cellIdx, PieceView pieceView, boolean isBlack) {
        this.cellIdx = cellIdx;
        this.pieceView = pieceView;
        if(isBlack) {
            this.viewColor = ViewColor.BLACK;
        } else {
            this.viewColor = ViewColor.WHITE;
        }
    }

    public int getCellIdx() {
        return this.cellIdx;
    }

    public boolean isValid() {
        return this.pieceView == null && this.viewColor == ViewColor.BLACK;
    }

    public PieceView getPieceView() {
        return pieceView;
    }

}
