package com.capstonedesign07.wormgame.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Room {

    private final static int ROOM_SIZE = 4;
    private String name;
    private Users users;
    private RoomStatus roomStatus;
    private List<WebSocketSession> sessions = new ArrayList<>();
    private boolean[][] attackCheckBoard;
    private int turn;

    public Room(String name, Users users) {
        this.name = name;
        this.users = users;
        this.roomStatus = RoomStatus.WAIT;
        users.setUsersRoom(this);
    }

    public Room(String name) {
        this(name, new Users());
    }

    public void send(ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(chatMessage.getMessage()));
        for(WebSocketSession wss : sessions)
            wss.sendMessage(textMessage);
    }

    public void send(int index, ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(chatMessage.getMessage()));
        sessions.get(index).sendMessage(textMessage);
    }

    public void sendCurrentPlayer(ObjectMapper objectMapper) throws IOException {
        for (int i = 1; i <= users.getSize(); i++) {
            TextMessage playerName = new TextMessage(" EVENT_PLAYER_NAME" + i + " : " + users.getUsers().get(i-1).getName() + ' ');
            TextMessage playerSessionId = new TextMessage(" EVENT_PLAYER_SESSIONID" + i + " : " + users.getUsers().get(i-1).getSessionId() + ' ');
            for (WebSocketSession wss : sessions) {
                wss.sendMessage(playerName);
                wss.sendMessage(playerSessionId);
            }
        }
        for (WebSocketSession wss : sessions)
            wss.sendMessage(new TextMessage(" EVENT_PLAYER_COUNT : " + users.getSize() + ' '));
    }

    public boolean canJoin() {
        if (users.getSize() < ROOM_SIZE && roomStatus.equals(RoomStatus.WAIT))
            return true;
        return false;
    }

    public boolean isInitializeFinish() {
        for (int i = 0; i < users.getSize(); i++) {
            User user = users.getUsers().get(i);
            if (user.getUserStatus().equals(UserStatus.LOSE))
                continue;
            if (user.getIsInitialized() == false)
                return false;
        }
        return true;
    }

    public boolean isOnlyOneWinner() {
        return users.isOnlyOneWinner();
    }

    public String getName() {
        return name;
    }

    public Users getUsers() {
        return users;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public List<WebSocketSession> getSessions() {
        return sessions;
    }

    public boolean[][] getAttackCheckBoard() {
        return attackCheckBoard;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setAttackCheckBoard(boolean[][] attackCheckBoard) {
        this.attackCheckBoard = attackCheckBoard;
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
