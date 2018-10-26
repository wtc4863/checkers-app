package com.webcheckers.model;

import java.lang.UnsupportedOperationException;
public class Move {

    Position start;
    Position end;

    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public Position getEnd() {
        return end;
    }

    public Position getStart() {
        return start;
    }

    /**
     * This method will not be used because a move must be a specific subclass of Move itsef.
     * If a plain Move is used then we throw the UnsupportedOperationException because
     * a generic move doesn't exist
     * @param board the board of the game we're validating
     * @return
     * @throws UnsupportedOperationException
     */
    public boolean validateMove(Board board) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    /**
     * This method will not be used because a move must be a specific subclass of Move itsef.
     * If a plain Move is used then we throw the UnsupportedOperationException because
     * a generic move doesn't exist
     * @param board the board of the game we're validating
     * @throws UnsupportedOperationException
     */
    public void executeMove(Board board) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Move)) {
            return false;
        } else {
            Move other = (Move)obj;
            return this.start.equals(other.start) && this.end.equals(other.end);
        }

    }
}
