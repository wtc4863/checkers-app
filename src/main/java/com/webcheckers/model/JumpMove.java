package com.webcheckers.model;

import java.util.ArrayList;

/**
 * A representation of a capture move, where a player's piece jumps over an
 * opponent piece to capture it. This move will move the piece two spaces
 * vertically and two spaces horizontally.
 */
public class JumpMove extends Move {

    Position middle;

    public JumpMove(Position start, Position end) {
        this(start, calculateMiddle(start, end), end);
    }

    /**
     * Helper constructor that is useful for testing, because sometimes we want
     * to inject a mock object for the middle position.
     *
     * @param start the start position of the move
     * @param middle the position that is jumped by the move
     * @param end the end position of the move
     */
    JumpMove(Position start, Position middle, Position end) {
        super(start, end);
        this.middle = middle;
    }

    /**
     * Calculate the "middle" position, AKA the position/space that this move
     * jumps over, given the starting and ending locations of the move.
     *
     * @param start the position the move starts at
     * @param end the position the move ends at
     * @return the calculated "middle" position
     */
    private static Position calculateMiddle(Position start, Position end) {
        int middleRow = (start.getRow() + end.getRow()) / 2;
        int middleCell = (start.getCell() + end.getCell()) / 2;
        return new Position(middleRow, middleCell);
    }

    /**
     * Helper method to verify the spacing of the move
     * @param start the starting position of the move
     * @param end the ending position of the move
     * @return true if the spacing is valid for a jump move, false otherwise
     */
    private static boolean validSpacing(Position start, Position end) {
        return (Math.abs(start.getRow() - end.getRow()) == 2 &&
                Math.abs(start.getCell() - end.getCell()) == 2);
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
        if(!validSpacing(this.start, this.end)) {
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
        try {
            if(!board.spaceIsValid(end)) {
                return false;
            }
        } catch(IndexOutOfBoundsException except) {
            // This is a really lazy way of checking that the ending position
            // is out of the board's bounds, but whatever.
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
        if(movedPiece.pieceColor == Piece.PColor.red) {
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
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks a position in a given game to see if it has any jump moves
     * available.
     *
     * @param pos the position to check for moves
     * @param game the game that is currently running
     * @return true if the position has jump moves available, false otherwise
     */
    public static boolean positionHasJumpMoveAvailable(Position pos, Game game) {
        ArrayList<JumpMove> possibleMoves = new ArrayList<>();
        /* REFERENCE FOR THIS CODE BLOCK:
        "lower" refers to lower-numbered rows
        "higher" refers to higher-numbered rows
         */

        // This is a big-time law of Demeter violation right here
        Piece.PColor currentColor = game.getBoard().getSpace(pos).pieceInfo().pieceColor;
        if(currentColor == Piece.PColor.red) {
            // Lower-left
            possibleMoves.add(new JumpMove(pos, new Position(pos.getRow() - 2, pos.getCell() - 2)));
            // Lower-right
            possibleMoves.add(new JumpMove(pos, new Position(pos.getRow() - 2, pos.getCell() + 2)));
        } else {
            // Upper-left
            possibleMoves.add(new JumpMove(pos, new Position(pos.getRow() + 2, pos.getCell() - 2)));
            // Upper-right
            possibleMoves.add(new JumpMove(pos, new Position(pos.getRow() + 2, pos.getCell() + 2)));
        }   // TODO: when the piece is a king, check all four of those

        // Check all the possible spaces to see if any one of them is valid
        for(JumpMove move : possibleMoves) {
            if(move.validateMove(game)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks a game to see if the current player has a jump move available.
     *
     * @param game the game state to check for possible jump moves
     * @return true if a jump move is available, false otherwise
     */
    public static boolean jumpMoveAvailable(Game game) {
        // Convert the turn to a PColor
        Piece.PColor currentColor = Piece.PColor.red;
        if(game.getTurn() == Game.Turn.WHITE) {
            currentColor = Piece.PColor.white;
        }

        // Loop through all piece positions for that player
        for(Position pos : game.getBoard().getPieceLocations(currentColor)) {
            if(positionHasJumpMoveAvailable(pos, game)) {
                return true;
            }
        }
        return false;
    }
}
