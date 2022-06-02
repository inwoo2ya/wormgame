package com.capstonedesign07.wormgame.repository;

import com.capstonedesign07.wormgame.domain.Room;
import java.util.List;

public interface RoomRepository {

    //    Room save(Room room);
    int findRoomIndex(Room room);
//    List<Room> findAll();
//    void delete(Room room);
    List<Room> getRooms();
}
