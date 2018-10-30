package com.webcheckers.model;

public class Position {
    int row;
    int cell;

    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    public int getCell() {
        return cell;
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
            return this.row == pos.getRow() && this.cell == pos.getCell();
        }
    }
    @Override
    public String toString() {
        return String.format("(%d, %d)", row, cell);
    }
}
