package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.webcheckers.model.Player;
import com.webcheckers.model.SimpleMove;
import com.webcheckers.ui.Message;
import com.webcheckers.ui.Message.MessageType;
import java.util.logging.Logger;

public class TurnController {
    private static final Logger LOG = Logger.getLogger(TurnController.class.getName());

    private static final String SIMPLE_MOVE_ERROR_MSG = "Piece not moved to adjacent space or Space is filled.";
    private static final String TOO_MANY_MOVES_ERROR_MSG = "Too many moves made";

    // Keep track of the moves made during this turn
    static int movesMade = 0;

    // Private attributes
    GsonBuilder builder;
    PlayerLobby playerLobby;

    public TurnController(PlayerLobby playerLobby) {
        builder = new GsonBuilder();
        this.playerLobby = playerLobby;
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
        Gson gson = this.builder.create();
        Move translatedMove = gson.fromJson(json, Move.class);

        // Do checking for move type in order to return correct type
        return new SimpleMove(translatedMove.getStart(), translatedMove.getEnd());
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
        boolean result = currentMove.validateMove(currentGame);
        // test if move is valid
        if(result) {
            // only one move per turn
            if(movesMade >= 1) {
                return returnMessageAndResetMoves(TOO_MANY_MOVES_ERROR_MSG, movesMade);
            }
            movesMade++;
            return new Message("Valid Move", MessageType.info);
        } else {
            // differentiate between different move types
            if(currentMove instanceof SimpleMove) {
                return returnMessageAndResetMoves(SIMPLE_MOVE_ERROR_MSG, movesMade);
            } else {
                return returnMessageAndResetMoves("GENERIC MOVE ERROR", movesMade);
            }
        }
    }

    private Message returnMessageAndResetMoves(String message, int currMoveCount) {
        movesMade = currMoveCount;
        return new Message(message, MessageType.error);
    }
}
