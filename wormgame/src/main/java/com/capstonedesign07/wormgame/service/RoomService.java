package com.capstonedesign07.wormgame.service;

import com.capstonedesign07.wormgame.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

//    public String add(Room room) {
//        validateDuplicateRoom(room);
//        roomRepository.save(room);
//        return room.getName();
//    }
//
//    private void validateDuplicateRoom(Room room) {
//        roomRepository.findByName(room.getName())
//                .ifPresent(r -> {
//                    throw new IllegalStateException("이미 존재하는 방입니다.");
//                });
//    }
//
//    public List<Room> findRooms() {
//        return roomRepository.findAll();
//    }
//
//    public Optional<Room> findOne(String roomName) {
//        return roomRepository.findByName(roomName);
//    }
}
