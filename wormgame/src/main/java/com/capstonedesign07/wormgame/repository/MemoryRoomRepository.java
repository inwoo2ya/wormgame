package com.capstonedesign07.wormgame.repository;

import com.capstonedesign07.wormgame.domain.Room;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryRoomRepository implements RoomRepository {

    private static Map<String, Room> store = new HashMap<>();

    @Override
    public Room save(Room room) {
        store.put(room.getName(), room);
        return room;
    }

    @Override
    public Optional<Room> findByName(String name) {
        return Optional.ofNullable(store.get(name));
    }

    @Override
    public List<Room> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(Room room) {

    }
}
