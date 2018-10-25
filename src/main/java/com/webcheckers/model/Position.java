package com.webcheckers.model;

public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Position)) {
            return false;
        } else {
            Position pos = (Position)obj;
            return this.row == pos.getRow() && this.col == pos.getRow();
        }
    }
}
