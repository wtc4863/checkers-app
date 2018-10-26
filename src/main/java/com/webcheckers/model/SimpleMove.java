package com.webcheckers.model;

public class SimpleMove extends Move{

    public SimpleMove(Position start, Position end) {
        super(start, end);
    }

    /**
     * This is an override so that when a SimpleMove is
     * created, it can be executed with different logic than that
     * of other moves.
     */
    @Override
    public void executeMove(Board board) {
        //TODO: move the piece from the start to the end
    }

    /**
     * This method checks if the selected space is a free
     * space
     * @param board the board where the move is being played
     * @return true if it is a valid move
     */
    @Override
    public boolean validateMove(Board board) {
        //TODO: check if space is empty
        //TODO check if backwards, check if piece is king, if true then can move backwards
        return false;
    }
}
