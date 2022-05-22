package com.capstonedesign07.wormgame.domain;

public class User {

    private String sessionId;
    private String name;
    private GameStatus gameStatus;

    public User(String sessionId, String name) {
        this.sessionId = sessionId;
        this.name = name;
        this.gameStatus = GameStatus.READY;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getName() {
        return name;
    }
}

enum GameStatus {
    READY, RUNNING, LOSE, WIN;
}