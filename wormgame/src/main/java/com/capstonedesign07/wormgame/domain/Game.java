package com.capstonedesign07.wormgame.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

public class Game {

    @Autowired private final static ObjectMapper objectMapper = new ObjectMapper();

    //백엔드 게임로직
    public static void gameInitialize(Room room) throws IOException {
        ChatMessage chatMessage = new ChatMessage();
        room.setAttackCheckBoard(new boolean[Position.BOARD_SIZE][Position.BOARD_SIZE]);

        room.setRoomStatus(RoomStatus.PLAYING);
        room.getUsers().setUsersStatus(UserStatus.RUNNING);
        room.getUsers().setUsersIsInitialized(false);

        chatMessage.setMessage("SYSTEM : 지렁이의 위치를 설정합니다.");
        room.send(chatMessage, objectMapper);

        //클라이언트에게 지렁이 3마리와 폭탄을 입력할 신호를 보냄
        chatMessage.setMessage("EVENT_INITIALIZE");
        room.send(chatMessage, objectMapper);
    }

    public static void gameRun(Room room) throws IOException {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setMessage("SYSTEM : 모든 유저가 지렁이와 폭탄을 설정했습니다.");
        room.send(chatMessage, objectMapper);

        chatMessage.setMessage("EVENT_USERS_WORM_AND_BOMB_COUNT : " + room.getUsers().usersWormsAndBombCount());
        room.send(chatMessage, objectMapper);

        //모든 유저 지렁이&폭탄 입력될 때 까지 대기(입력한 클라이언트들에게는 다른 유저의 입력을 기다리는 중이라 표시)

        //승자가 가려질 때 까지 반복
            //클라이언트한테 공격할 좌표를 입력할 신호를 보냄

            //모든 클라이언트가 공격 좌표를 입력할 때 까지 대기(입력한 클라이언트들에게는 다른 유저의 입력을 기다리는 중이라 표시)

            //공격 처리

        //승자를 출력

        //유저와 방 상태 변경

        //게임이 끝났다는 신호를 전송
    }
}
