package com.webcheckers.ui;

import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class TurnController {

    private GsonBuilder builder;
    private Game modelRepresentation;
    private BoardView viewRepresentation;

    /**
     * @param currentGame the game being played
     * @param viewBoard the board of the game after a move has been sent
     */
    public TurnController(Game currentGame, BoardView viewBoard) {
        builder = new GsonBuilder();
        modelRepresentation = currentGame;
        viewRepresentation = viewBoard;
    }

    /**
     * Takes a JSON object that holds the information for a move
     * and creates a model-tier value object to represent with the same information
     * @param json the json-formatted move
     * @return a Move instance
     */
    public Move fromUItoModel(String json) {
        Gson gson = this.builder.create();
        Move translatedMove = gson.fromJson(json, Move.class);
        return translatedMove;
    }

    /**
     * Takes a move instance and uses its data to create a json representation
     * to pass to be understood by the ui tier
     * @param move the instance of move to translate
     * @return a JSON-formatted string
     */
    public String fromModeltoUI(Move move) {
        Gson gson = builder.create();
        String translatedMove = gson.toJson(move);
        return translatedMove;
    }
}
