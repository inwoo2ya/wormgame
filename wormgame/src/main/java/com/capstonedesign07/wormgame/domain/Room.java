package com.capstonedesign07.wormgame.domain;

import com.capstonedesign07.wormgame.repository.MemoryUserRepository;
import com.capstonedesign07.wormgame.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Room {

    private final static int ROOM_SIZE = 4;
    @Autowired private final static UserRepository userRepository = new MemoryUserRepository();
    private String name;
    private Users users;
    private RoomStatus roomStatus;
    private List<WebSocketSession> sessions = new ArrayList<>();

    public Room(String name, Users users) {
        this.name = name;
        this.users = users;
        this.roomStatus = RoomStatus.WAIT;
        users.setUsersRoom(this);
    }

    public Room(String name) {
        this(name, new Users());
    }

    public void handleMessage(WebSocketSession session, ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        if (chatMessage.getMessageType() == MessageType.ENTER) {
            sessions.add(session);
            chatMessage.setMessage("SYSTEM : " + chatMessage.getWriter() + "님이 입장하셨습니다.");
        }
        if (chatMessage.getMessageType() == MessageType.LEAVE) {
            sessions.remove(session);
            chatMessage.setMessage("SYSTEM : " + chatMessage.getWriter() + "님이 퇴장하셨습니다.");
            User user = userRepository.findByName(chatMessage.getWriter());
            Room room = user.getRoom();
            room.removeUser(user);
        }
        if (chatMessage.getMessageType() == MessageType.CHAT)
            chatMessage.setMessage(chatMessage.getWriter() + " : " + chatMessage.getMessage());
        System.out.println("sessions.size() = " + sessions.size());
        send(chatMessage, objectMapper);
    }

    private void send(ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(chatMessage.getMessage()));
        for(WebSocketSession wss : sessions)
            wss.sendMessage(textMessage);
    }

    public boolean canJoin() {
        if (users.getSize() < ROOM_SIZE && roomStatus.equals(RoomStatus.WAIT))
            return true;
        return false;
    }

    public String getName() {
        return name;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public void addUser(User user) {
        if (canJoin()) {
            users.addUser(user);
            user.setRoom(this);
        }
        validateUsersSize();
    }

    public void removeUser(User user) {
        users.removeUser(user);
        user.setRoom(null);
        validateUsersSize();
    }

    public List<User> roomUsers() {
        return users.getUsers();
    }

    private void validateUsersSize() {
        if (users.getSize() < 0 || users.getSize() > ROOM_SIZE) {
            throw new IllegalArgumentException(name + " 방의 인원수가 잘못됨");
        }
    }
}
