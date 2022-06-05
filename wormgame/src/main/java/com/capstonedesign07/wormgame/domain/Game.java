package com.capstonedesign07.wormgame.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class Game {

    @Autowired private final static ObjectMapper objectMapper = new ObjectMapper();

    //백엔드 게임로직
    public static void run(Room room) throws IOException {
        ChatMessage chatMessage = new ChatMessage();

        room.setRoomStatus(RoomStatus.PLAYING);
        room.getUsers().setUsersStatus(UserStatus.RUNNING);

        chatMessage.setMessage("SYSTEM : 지렁이의 위치를 설정합니다<미구현>.");
        room.send(chatMessage, objectMapper);
    }
}
