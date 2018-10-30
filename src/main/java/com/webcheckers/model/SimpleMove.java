package com.webcheckers.model;

import com.webcheckers.model.Game.Turn;

import java.util.logging.Logger;

public class SimpleMove extends Move {
    private static final Logger LOG = Logger.getLogger(SimpleMove.class.getName());

    public SimpleMove(Position start, Position end) {
        super(start, end);
    }

    /**
     * This is an override so that when a SimpleMove is
     * created, it can be executed with different logic than that
     * of other moves.
     */
    @Override
    public boolean executeMove(Game game) {
        Board board = game.getBoard();
        if (validateMove(game)) {
            board.move(start, end);
            game.switchTurn();
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method checks if the selected space is results in a valid
     * move
     * <p>
     * NOTE: since this is only used for move validation, the client will
     * only send us requests if the piece is already inside the board
     *
     * @param game the game on which we're applying the moves
     * @return true if it is a valid move
     */
    @Override
    public boolean validateMove(Game game) {
        //check if there is a piece on it
        Board board = game.getBoard();
        if (board.spaceIsValid(end)) {
            // red is at the top of the board in the model and moving "forward" is going down
            //TODO: does the way we flip the board change this because the client will switch it?
            if (game.getTurn() == Turn.WHITE) {
                // get the possible starting points given this ending space
                int left = end.getCell() - 1;
                int right = end.getCell() + 1;
                int top = end.getRow() - 1;
                // create new positions for easy comparison
                Position upperLeft = new Position(top, left);
                Position upperRight = new Position(top, right);
                return upperLeft.equals(start) || upperRight.equals(start);
            } else {
                // get the possible starting points given this ending space
                int left = end.getCell() - 1;
                int right = end.getCell() + 1;
                int bot = end.getRow() + 1;
                // create new positions for easy comparison
                Position bottomLeft = new Position(bot, left);
                Position bottomRight = new Position(bot, right);
                return bottomLeft.equals(start) || bottomRight.equals(start);
            }
        } else {
            return false;
        }
    }

    /**
     * This method runs a general check if the move is a simple move. Not checking if
     * there is a piece on the space. This means it just checks the 4 diagnoally adjacent
     * spaces to see if any of them is the end space.
     *
     * @param move the move to check
     * @return true if the start pos matches any of the expected start positions for the space
     */
    public static boolean isSimpleMove(Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();
        // get the possible starting points given this ending space
        int left = end.getCell() - 1;
        int right = end.getCell() + 1;
        int top = end.getRow() + 1;
        int bot = end.getRow() - 1;
        // create new positions for easy comparison
        Position upperLeft = new Position(top, left);
        Position upperRight = new Position(top, right);
        Position bottomLeft = new Position(bot, left);
        Position bottomRight = new Position(bot, right);
        return upperLeft.equals(start) || upperRight.equals(start) || bottomLeft.equals(start) || bottomRight.equals(start);
    }

    @Override
    public String toString() {
        return String.format("SimpleMove{(%d, %d) -> (%d, %d)}",
                start.getRow(), start.getCell(), end.getRow(), end.getCell());
    }

}
