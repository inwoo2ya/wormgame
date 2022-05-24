package com.capstonedesign07.wormgame.domain;

import java.util.Objects;

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

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    @Override
    public boolean equals(final Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User)o;
        return Objects.equals(sessionId, user.sessionId) && Objects.equals(name, user.name) && Objects.equals(gameStatus, user.gameStatus);
    }
}