package com.webcheckers.model;

import com.webcheckers.model.Game.Turn;

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
    public boolean executeMove(Game game) {
        Board board = game.getBoard();
        if(validateMove(game)) {
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
     *
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
        if(board.spaceIsValid(end)) {
            // red is at the top of the board in the model and moving "forward" is going down
            //TODO: does the way we flip the board change this because the client will switch it?
            if(game.getTurn() == Turn.WHITE) {
                // get the possible starting points given this ending space
                int left = end.getCol() - 1;
                int right = end.getCol() + 1;
                int top = end.getRow() + 1;
                // create new positions for easy comparison
                Position upperLeft = new Position(top, left);
                Position upperRight = new Position(top, right);
                return upperLeft.equals(start) || upperRight.equals(start);
            } else {
                // get the possible starting points given this ending space
                int left = end.getCol() - 1;
                int right = end.getCol() + 1;
                int bot = end.getRow() - 1;
                // create new positions for easy comparison
                Position bottomLeft = new Position(bot, left);
                Position bottomRight = new Position(bot, right);
                return bottomLeft.equals(start) || bottomRight.equals(start);
            }
        } else {
            return false;
        }
     }

}
