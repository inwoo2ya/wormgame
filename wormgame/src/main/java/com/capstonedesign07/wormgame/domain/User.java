package com.capstonedesign07.wormgame.domain;

import java.util.Objects;
import java.util.stream.IntStream;

public class User {

    private String sessionId;
    private String name;
    private UserStatus userStatus;
    private Room room;
    private char[][] viewBoard;
    private Worms worms;
    private Bomb bomb;
    private boolean isInitialized;
    private boolean isAttacked;

    public User(String sessionId, String name) {
        this.sessionId = sessionId;
        this.name = name;
        this.userStatus = UserStatus.READY;
    }

    public void initialize(String string) {
        if (string.length() != 20) {
            throw new IllegalArgumentException("초기화 문자열의 길이가 20가 아님");
        }
        viewBoard = new char[Position.BOARD_SIZE][Position.BOARD_SIZE];

        this.worms = new Worms(string.substring(0, 18), viewBoard);
        this.bomb = new Bomb(new Position(string.charAt(18) - '0', string.charAt(19) - '0'), viewBoard);
        isInitialized = true;
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

    public char[][] getViewBoard() {
        return viewBoard;
    }

    public Worms getWorms() {
        return worms;
    }

    public Bomb getBomb() {
        return bomb;
    }

    public boolean getIsInitialized() {
        return isInitialized;
    }

    public boolean getIsAttacked() {
        return isAttacked;
    }

    public void setIsAttacked(boolean isAttacked) {
        this.isAttacked = isAttacked;
    }

    public void setIsInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    public int getLivingWormsCount() {
        if (userStatus.equals(UserStatus.ESCAPE) || worms == null)
            return 0;

        int count = 0;
        for (int i = 0; i < worms.getWorms().size() ; i++)
            if (getWorms().getWorms().get(i).isAlive())
                count++;
        return count;
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