package com.capstonedesign07.wormgame.repository;

import com.capstonedesign07.wormgame.domain.Room;
import java.util.List;

public interface RoomRepository {

    //    Room save(Room room);
//    Optional<Room> findByName(String name);
//    List<Room> findAll();
//    void delete(Room room);
    List<Room> getRooms();
}
