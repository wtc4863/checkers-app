package com.webcheckers.model;

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
