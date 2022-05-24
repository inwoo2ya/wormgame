package com.capstonedesign07.wormgame.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Rooms {

    private final static int FIXED_ROOM_COUNT = 10;
    private List<Room> rooms;

    public Rooms(String roomName) {
        if(rooms == null) {
            rooms = Collections.unmodifiableList(
                    IntStream.rangeClosed(1, FIXED_ROOM_COUNT)
                            .mapToObj(i -> new Room(roomName + " " + i))
                            .collect(Collectors.toList())
            );
        }
    }

    public int getSize() {
        return rooms.size();
    }

    public Room findRoomByIndex(int index) {
        validateIndex(index);
        return rooms.get(index);
    }

    public Room findRoomByName(String name) {
        List<Room> foundUser = IntStream.range(0, FIXED_ROOM_COUNT)
                .filter(i -> rooms.get(i).getName().equals(name))
                .mapToObj(rooms::get)
                .collect(Collectors.toList());
        if(foundUser.size() != 1) {
            throw new IllegalArgumentException("findRoomByName : room이 여러개이거나 찾지 못함");
        }
        return foundUser.get(0);
    }

    private void validateIndex(int index) {
        if (index >= FIXED_ROOM_COUNT || index < 0) {
            throw new IllegalArgumentException("findRoomByIndex : 잘못된 index 접근");
        }
    }

    public List<Room> getRooms() {
        return Collections.unmodifiableList(new ArrayList<>(rooms));
    }
}
