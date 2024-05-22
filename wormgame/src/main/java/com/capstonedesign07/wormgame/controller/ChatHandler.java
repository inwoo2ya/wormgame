package com.capstonedesign07.wormgame.controller;

import com.capstonedesign07.wormgame.domain.*;
import com.capstonedesign07.wormgame.repository.RoomRepository;
import com.capstonedesign07.wormgame.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.List;

@Component
public class ChatHandler extends TextWebSocketHandler {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ChatHandler(RoomRepository roomRepository, UserRepository userRepository, ObjectMapper objectMapper) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("메세지 전송 = " + session + " : " + message.getPayload());
        String msg = message.getPayload();
        System.out.println("msg = " + msg);
        ChatMessage chatMessage = objectMapper.readValue(msg, ChatMessage.class);
        Room room = roomRepository.findRoomByName(chatMessage.getChatRoomName());
        handleMessage(room, session, chatMessage, objectMapper);
    }

    public void handleMessage(Room room, WebSocketSession session, ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        List<WebSocketSession> sessions = room.getSessions();
        User user = userRepository.findBySessionId(chatMessage.getWriter());
        boolean gameStart = false;
        boolean sendMessage = false;

        if (chatMessage.getMessageType() == MessageType.ENTER) {
            sessions.add(session);
            chatMessage.setMessage("SYSTEM : " + user.getName() + "님이 입장하셨습니다.");
            sendMessage = true;
            room.sendCurrentPlayer(objectMapper);
        }
        if (chatMessage.getMessageType() == MessageType.LEAVE && room.getRoomStatus().equals(RoomStatus.WAIT)) {
            sessions.remove(session);
            chatMessage.setMessage("SYSTEM : " + user.getName() + "님이 퇴장하셨습니다.");
            sendMessage = true;
            room.removeUser(user);
            room.sendCurrentPlayer(objectMapper);
        }
        if (chatMessage.getMessageType() == MessageType.LEAVE && room.getRoomStatus().equals(RoomStatus.PLAYING)) {
            user.setUserStatus(UserStatus.ESCAPE);
            chatMessage.setMessage("SYSTEM : " + user.getName() + "님이 탈주하셨습니다.");
            room.send(chatMessage, objectMapper);
//            user.setName(user.getName() + " (탈주)");
//            room.sendCurrentPlayer(objectMapper);

//            user.setIsInitialized(true);
//            user.setIsAttacked(true);

            if (room.getUsers().nonEscapeUsersCount() <= 1) {
                room.gameEnd();
                room.sendCurrentPlayer(objectMapper);
            }

            //초기화 단계에서 탈주일 때
            if (!user.getIsInitialized()) {
                user.setIsInitialized(true);

                if (room.isInitializeFinish()) {
                    room.gameRun();
                    chatMessage.setMessage("EVENT_YOUR_TURN");
                    room.send(chatMessage, objectMapper);
                    room.getUsers().setUsersIsAttacked(false);
                }
            }
            //턴에서 탈주일 때


        }
        if (chatMessage.getMessageType() == MessageType.CHAT) {
            chatMessage.setMessage(user.getName() + " : " + chatMessage.getMessage());
            sendMessage = true;
        }
        if (chatMessage.getMessageType() == MessageType.GAMESTART && room.getUsers().getSize() > 1) {
            if (!user.equals(room.roomUsers().get(0))) {
                throw new IllegalArgumentException("게임을 시작한 플레이어가 1p가 아님");
            }
            gameStart = true;
            chatMessage.setMessage("SYSTEM : " + user.getName() + "님이 게임을 시작하셨습니다.");
            sendMessage = true;
        }
        if (chatMessage.getMessageType() == MessageType.INITIALIZED) {
            if (room.getRoomStatus().equals(RoomStatus.WAIT)) {
                throw new IllegalArgumentException("대기중인 게임에서 지렁이와 폭탄이 초기화됨");
            }
            user.initialize(chatMessage.getMessage());
            if (room.isInitializeFinish()) {
                room.gameRun();
                chatMessage.setMessage("EVENT_YOUR_TURN");
                room.send(chatMessage, objectMapper);
                room.getUsers().setUsersIsAttacked(false);
            }
        }
        if (chatMessage.getMessageType() == MessageType.ATTACK) {
            room.getAttackUserQueue().add(user);
            room.getAttackPositionQueue().add(new Position(chatMessage.getMessage()));
            user.setIsAttacked(true);

            if (room.getUsers().turnAttackFinished()) {
                room.attack();

                chatMessage.setMessage("EVENT_USERS_WORM_AND_BOMB_COUNT : " + room.getUsers().usersWormsAndBombCount());
                room.send(chatMessage, objectMapper);

                if (!room.isGameOver()) {
                    chatMessage.setMessage("EVENT_YOUR_TURN");
                    room.send(chatMessage, objectMapper);
                    room.getUsers().setUsersIsAttacked(false);
                } else {
                    room.gameEnd();
                }
            }
        }

        if (sendMessage)
            send(room, chatMessage, objectMapper);
        if (gameStart)
            room.gameInitialize();
    }

    private void send(Room room, ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        room.send(chatMessage, objectMapper);
    }
}
