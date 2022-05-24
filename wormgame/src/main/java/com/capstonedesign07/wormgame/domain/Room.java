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

    public String getName() {
        return name;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void addUser(User user) {
        users.addUser(user);
    }

    public void removeUser(User user) {
        users.removeUser(user);
    }

    public List<User> roomUsers() {
        return users.getUsers();
    }
}
