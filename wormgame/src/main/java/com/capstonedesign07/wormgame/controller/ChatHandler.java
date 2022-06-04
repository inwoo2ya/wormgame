package com.capstonedesign07.wormgame.controller;

import com.capstonedesign07.wormgame.domain.ChatMessage;
import com.capstonedesign07.wormgame.domain.MessageType;
import com.capstonedesign07.wormgame.domain.Room;
import com.capstonedesign07.wormgame.domain.User;
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
        ChatMessage chatMessage = objectMapper.readValue(msg, ChatMessage.class);
        Room room = roomRepository.findRoomByName(chatMessage.getChatRoomName());
        handleMessage(room, session, chatMessage, objectMapper);
    }

    public void handleMessage(Room room, WebSocketSession session, ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        List<WebSocketSession> sessions = room.getSessions();
        User user = userRepository.findBySessionId(chatMessage.getWriter());
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
        send(room, chatMessage, objectMapper);
    }

    private void send(Room room, ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        room.send(chatMessage, objectMapper);
    }
}
