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
     * @param game the game in which we're moving the piece
     * @throws UnsupportedOperationException
     */
    public boolean validateMove(Game game) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    /**
     * This method will not be used because a move must be a specific subclass of Move itsef.
     * If a plain Move is used then we throw the UnsupportedOperationException because
     * a generic move doesn't exist
     * @param game the game in which we're moving the piece
     * @return true if move was made
     * @throws UnsupportedOperationException
     */
    public boolean executeMove(Game game) throws UnsupportedOperationException{
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
