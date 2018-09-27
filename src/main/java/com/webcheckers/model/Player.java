package com.webcheckers.model;

public class Player {
    private String sessionID;
    private final String name;

    public Player(String name) {
        this.name = name;
        this.sessionID = "";
    }
    public Player(String name, String sid) {
        this.name = name;
        this.sessionID = sid;
    }

    public String getName() {
        return this.name;
    }

    public void updateSessionID(String newID) {
        this.sessionID = newID;
    }



}
