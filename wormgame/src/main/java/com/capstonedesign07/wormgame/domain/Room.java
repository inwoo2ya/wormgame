package com.capstonedesign07.wormgame.domain;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private String name;
    private int roomSize;
    private ArrayList<User> userArrayList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }

    public void addUser(User user) {
        userArrayList.add(user);
    }

    public void delUser(User user) {
        userArrayList.remove(user);
    }

    public List<User> findAll() {
        return userArrayList;
    }
}
