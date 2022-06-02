package com.capstonedesign07.wormgame.repository;

import com.capstonedesign07.wormgame.domain.Room;
import java.util.List;

public interface RoomRepository {

    int findRoomIndex(Room room);
    Room findRoomByName(String name);
    List<Room> getRooms();
}
