package com.capstonedesign07.wormgame.controller;

import com.capstonedesign07.wormgame.domain.ChatMessage;
import com.capstonedesign07.wormgame.domain.Room;
import com.capstonedesign07.wormgame.repository.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatHandler extends TextWebSocketHandler {

    //    private static List<WebSocketSession> list = new ArrayList<>();
    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ChatHandler(RoomRepository roomRepository, ObjectMapper objectMapper) {
        this.roomRepository = roomRepository;
        this.objectMapper = objectMapper;
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("메세지 전송 = " + session + " : " + message.getPayload());
        String msg = message.getPayload();
//        System.out.println("msg = " + msg);
        ChatMessage chatMessage = objectMapper.readValue(msg, ChatMessage.class);
        Room room = roomRepository.findRoomByName(chatMessage.getChatRoomName());
        room.handleMessage(session, chatMessage, objectMapper);
    }
}
