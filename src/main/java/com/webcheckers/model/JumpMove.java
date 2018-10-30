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
     * Check that this move is valid for a given game.
     *
     * @param game the game in which we're moving the piece
     * @return true if the move is valid, false if the move is invalid
     */
    @Override
    public boolean validateMove(Game game) {
        // Init variables
        Piece movedPiece;
        Piece jumpedPiece;

        // Make sure the spacing is right
        if(Math.abs(start.getRow() - end.getRow()) != 2 || Math.abs(start.getCell() - end.getCell()) != 2) {
            return false;
        }

        // Make sure the starting position has a piece
        Board board = game.getBoard();
        if(board.getSpace(start).doesHasPiece()) {
            // We'll need this information later
            movedPiece = board.getSpace(start).pieceInfo();
        } else {
            return false;
        }

        // Make sure the ending position doesn't have a piece
        if(!board.spaceIsValid(end)) {
            return false;
        }

        // Make sure the middle position has an opponent piece
        if(board.getSpace(middle).doesHasPiece()) {
            jumpedPiece = board.getSpace(middle).pieceInfo();
            if(jumpedPiece.pieceColor == movedPiece.pieceColor) {
                // Can only jump an opponent's piece
                return false;
            }
        } else {
            return false;
        }

        // TODO: skip this block if the piece is a king
        // Make sure the piece is going in the right direction
        if(jumpedPiece.pieceColor == Piece.PColor.red) {
            // Red pieces should travel in the "negative" direction
            return start.getRow() > end.getRow();
        } else {
            // White pieces should travel in the "positive" direction
            return start.getRow() < end.getRow();
        }
    }

    /**
     * Apply this move to a given game
     *
     * @param game the game in which we're moving the piece
     * @return true if the move was successfully executed, false otherwise
     */
    @Override
    public boolean executeMove(Game game) {
        // Double-check that the move is valid
        if(validateMove(game)) {
            Board board = game.getBoard();
            // Move the piece
            board.move(start, end);

            // Remove the jumped piece
            board.getSpace(middle).removePiece();
        } else {
            return false;
        }
    }
}
