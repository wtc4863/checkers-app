package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.webcheckers.model.Player;
import com.webcheckers.model.SimpleMove;

public class TurnController {

    private GsonBuilder builder;
    private PlayerLobby playerLobby;
    //private Game modelRepresentation;
    //private BoardView viewRepresentation;

    public TurnController(PlayerLobby playerLobby) {
        builder = new GsonBuilder();
        this.playerLobby = playerLobby;
    }

    /**
     * Return the message in a json format
     * @param message the message to be translated
     * @return a string continaing a message
     */
     String MessageFromModeltoUI(Message message) {
        Gson gson = this.builder.create();
        return gson.toJson(message, Message.class);
    }

    /**
     * Takes a JSON object that holds the information for a move
     * and creates a model-tier value object to represent with the same information
     * @param json the json-formatted move
     * @return a Move instance
     */
    private Move MovefromUItoModel(String json) {
        Gson gson = this.builder.create();
        Move translatedMove = gson.fromJson(json, Move.class);

        // Do checking for move type in order to return correct type
        if(SimpleMove.isSimpleMove(translatedMove)) {
            return new SimpleMove(translatedMove.getStart(), translatedMove.getEnd());
        } else {
            // if we get here then something went wrong
            return null;
        }
    }

    /**
     * Takes a move instance and uses its data to create a json representation
     * to pass to be understood by the ui tier
     * @param move the instance of move to translate
     * @return a JSON-formatted string
     */
    private String MovefromModeltoUI(Move move) {
        Gson gson = builder.create();
        String translatedMove = gson.toJson(move);
        return translatedMove;
    }

    /**
     * Starts the validation in the model tier for the given move.
     * @param moveToBeValidated the move in JSON format
     * @param sessionID the session ID that will help find the game
     * @return
     */
    public boolean handleValidation(String moveToBeValidated, String sessionID) {
        Player playerMakingMove = playerLobby.getPlayerBySessionID(sessionID);
        Game currentGame = playerLobby.getGame(playerMakingMove);
        Move currentMove = MovefromUItoModel(moveToBeValidated);

        if(currentMove != null) {
            return currentMove.validateMove(currentGame);
        } else {
            return false;
        }
    }
}
