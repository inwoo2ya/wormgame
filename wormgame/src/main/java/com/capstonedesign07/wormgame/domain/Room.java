package com.capstonedesign07.wormgame.domain;

import java.util.List;

public class Room {

    private final static int ROOM_SIZE = 4;
    private String name;
    private Users users;
    private RoomStatus roomStatus;

    public Room(String name, Users users) {
        this.name = name;
        this.users = users;
        this.roomStatus = RoomStatus.WAIT;
    }

    public Room(String name) {
        this(name, new Users());
    }

    public boolean canJoin() {
        if (users.getSize() < ROOM_SIZE)
            return true;
        return false;
    }

    public String getName() {
        return name;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void addUser(User user) {
        if (canJoin()) {
            users.addUser(user);
        }
        validateUsersSize();
    }

    public void removeUser(User user) {
        users.removeUser(user);
        validateUsersSize();
    }

    public List<User> roomUsers() {
        return users.getUsers();
    }

    private void validateUsersSize() {
        if (users.getSize() < 0 || users.getSize() > ROOM_SIZE) {
            throw new IllegalArgumentException(name + " 방의 인원수가 잘못됨");
        }
    }
}
