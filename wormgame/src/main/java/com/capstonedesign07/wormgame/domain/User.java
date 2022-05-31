package com.capstonedesign07.wormgame.domain;

import java.util.Objects;

public class User {

    private String sessionId;
    private String name;
    private UserStatus userStatus;
    private Room room;

    public User(String sessionId, String name) {
        this.sessionId = sessionId;
        this.name = name;
        this.userStatus = UserStatus.READY;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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
        return Objects.equals(sessionId, user.sessionId) && Objects.equals(name, user.name) && Objects.equals(userStatus, user.userStatus);
    }
}