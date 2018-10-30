package com.webcheckers.model;

public class JumpMove extends Move {

    Position middle;

    public JumpMove(Position start, Position end) {
        super(start, end);

        // Calculate the middle position
        int middleRow = (start.getRow() + end.getRow()) / 2;
        int middleCell = (start.getCell() + end.getCell()) / 2;
        this.middle = new Position(middleRow, middleCell);
    }

    /**
     * This method will not be used because a move must be a specific subclass of Move itsef.
     * If a plain Move is used then we throw the UnsupportedOperationException because
     * a generic move doesn't exist
     *
     * @param game the game in which we're moving the piece
     * @throws UnsupportedOperationException
     */
    @Override
    public boolean validateMove(Game game) throws UnsupportedOperationException {
        return super.validateMove(game);
    }

    /**
     * This method will not be used because a move must be a specific subclass of Move itsef.
     * If a plain Move is used then we throw the UnsupportedOperationException because
     * a generic move doesn't exist
     *
     * @param game the game in which we're moving the piece
     * @return true if move was made
     * @throws UnsupportedOperationException
     */
    @Override
    public boolean executeMove(Game game) throws UnsupportedOperationException {
        return super.executeMove(game);
    }
}
