package com.capstonedesign07.wormgame.repository;

import com.capstonedesign07.wormgame.domain.Room;
import com.capstonedesign07.wormgame.domain.Rooms;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class MemoryRoomRepository implements RoomRepository {

    private final static int FIXED_ROOM_COUNT = 8;
    private final static Rooms store = new Rooms("임시 방제목");

    @Override
    public int findRoomIndex(Room room) {
        List<Room> rooms = store.getRooms();
        for (int i = 0; i < FIXED_ROOM_COUNT; i++)
            if (room == rooms.get(i))
                return i;
        return -1;
    }

    @Override
    public Room findRoomByName(String name) {
        return store.findRoomByName(name);
    }

    @Override
    public List<Room> getRooms() {
        return store.getRooms();
    }
}
