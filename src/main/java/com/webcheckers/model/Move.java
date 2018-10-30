package com.webcheckers.model;

import java.lang.UnsupportedOperationException;
public class Move {

    static final String GENERIC_VALIDATION_ERROR_MESSAGE = "Generic move validation not supported.";
    static final String GENERIC_EXECUTION_ERROR_MESSAGE = "Generic move execution not supported.";


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
        throw new UnsupportedOperationException(GENERIC_VALIDATION_ERROR_MESSAGE);
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
        throw new UnsupportedOperationException(GENERIC_EXECUTION_ERROR_MESSAGE);
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

    @Override
    public String toString() {
        return String.format("Move{(%d, %d) -> (%d, %d)}",
            start.getRow(), start.getCell(), end.getRow(), end.getCell());
    }
}
