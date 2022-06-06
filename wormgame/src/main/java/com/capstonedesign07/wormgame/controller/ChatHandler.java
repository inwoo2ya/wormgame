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

        if (chatMessage.getMessageType() == MessageType.ENTER) {
            sessions.add(session);
            chatMessage.setMessage("SYSTEM : " + user.getName() + "님이 입장하셨습니다.");
        }
        if (chatMessage.getMessageType() == MessageType.LEAVE) {
            sessions.remove(session);
            chatMessage.setMessage("SYSTEM : " + user.getName() + "님이 퇴장하셨습니다.");
            room.removeUser(user);
        }
        if (chatMessage.getMessageType() == MessageType.CHAT)
            chatMessage.setMessage(user.getName() + " : " + chatMessage.getMessage());
        if (chatMessage.getMessageType() == MessageType.GAMESTART && room.getUsers().getSize() > 1) {
            if (!user.equals(room.roomUsers().get(0))) {
                throw new IllegalArgumentException("게임을 시작한 플레이어가 1p가 아님");
            }
            gameStart = true;
            chatMessage.setMessage("SYSTEM : " + user.getName() + "님이 게임을 시작하셨습니다.");
        }

        send(room, chatMessage, objectMapper);
        if (gameStart)
            Game.run(room);
    }

    private void send(Room room, ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        room.send(chatMessage, objectMapper);
    }
}
