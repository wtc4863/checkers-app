package com.webcheckers.appl;

import com.webcheckers.model.*;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.webcheckers.ui.Message;
import com.webcheckers.ui.Message.MessageType;
import java.util.logging.Logger;

public class TurnController {
    private static final Logger LOG = Logger.getLogger(TurnController.class.getName());

    enum InvalidMoveTypes {
        TOO_MANY_MOVES,
        SIMPLE_MOVE_ERROR,
        TOO_MANY_JUMPS,
        PIECE_KINGED

    }

    static final String JUMP_MOVE_ERROR_MSG = "You must jump exactly one opponent piece.";
    static final String SIMPLE_MOVE_ERROR_MSG = "You must move a piece to an empty adjacent space.";
    static final String TOO_MANY_SIMPLE_MOVES_ERROR_MSG = "You may only make one simple move per turn.";
    static final String VALID_MOVE = "Valid move!";
    static final String GENERIC_MOVE_ERR = "GENERIC MOVE ERROR";

    private boolean simpleMoveMade;

    // Private attributes
    GsonBuilder builder;
    PlayerLobby playerLobby;

    public TurnController(PlayerLobby playerLobby) {
        builder = new GsonBuilder();
        this.playerLobby = playerLobby;
        this.simpleMoveMade = false;
    }

    /**
     * Return the message in a json format
     * @param message the message to be translated
     * @return a string continaing a message
     */
     public String MessageFromModeltoUI(Message message) {
        Gson gson = this.builder.create();
        return gson.toJson(message, Message.class);
    }

    /**
     * Takes a JSON object that holds the information for a move
     * and creates a model-tier value object to represent with the same information
     * @param json the json-formatted move
     * @return a Move instance
     */
    Move MovefromUItoModel(String json) {
        LOG.fine(json);
        Gson gson = this.builder.create();
        Move translatedMove = gson.fromJson(json, Move.class);

        // Do checking for move type in order to return correct type
        if(SimpleMove.isSimpleMove(translatedMove)) {
            return new SimpleMove(translatedMove.getStart(), translatedMove.getEnd());
        } else {
            return new JumpMove(translatedMove.getStart(), translatedMove.getEnd());
        }
    }

    /**
     * Starts the validation in the model tier for the given move.
     * @param moveToBeValidated the move in JSON format
     * @param sessionID the session ID that will help find the game
     * @return
     */
    public Message handleValidation(String moveToBeValidated, String sessionID) {
        Player playerMakingMove = playerLobby.getPlayerBySessionID(sessionID);
        Game currentGame = playerLobby.getGame(playerMakingMove);
        Move currentMove = MovefromUItoModel(moveToBeValidated);
        //int movesMade = currentGame.getMovesInCurrentTurn();
        boolean result = currentMove.validateMove(currentGame);
        // test if move is valid
        if(result) {
            // only one simple move per turn
            if (this.simpleMoveMade) {
                return returnMessageAndResetMoves(TOO_MANY_SIMPLE_MOVES_ERROR_MSG, true);
            } else if(currentMove instanceof SimpleMove){
                this.simpleMoveMade = true;
            }
            currentGame.addMove(currentMove);
            return new Message(VALID_MOVE, MessageType.info);
        } else {
            // differentiate between different errors move types
            if(currentMove instanceof SimpleMove) {
                return returnMessageAndResetMoves(SIMPLE_MOVE_ERROR_MSG, this.simpleMoveMade);
            } else if(currentMove instanceof JumpMove) {
                return returnMessageAndResetMoves(JUMP_MOVE_ERROR_MSG, this.simpleMoveMade);
            } else {
                // we should never get here
                return returnMessageAndResetMoves(GENERIC_MOVE_ERR, this.simpleMoveMade);
            }
        }
    }

    private Message returnMessageAndResetMoves(String message, boolean simpleMoveMade) {
        this.simpleMoveMade = simpleMoveMade;
        return new Message(message, MessageType.error);
    }

    /**
     * Reset the moves counter to zero, such as when the user has submitted
     * their turn.
     */
    public void resetMoves() {
        this.simpleMoveMade = false;
    }
}
