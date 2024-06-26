package com.capstonedesign07.wormgame.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Room {

    private final static int ROOM_SIZE = 4;
    private String name;
    private Users users;
    private RoomStatus roomStatus;
    private List<WebSocketSession> sessions = new ArrayList<>();
    private boolean[][] attackCheckBoard;
    private int turn;
    private Queue<User> attackUserQueue;
    private Queue<Position> attackPositionQueue;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Room(String name, Users users) {
        this.name = name;
        this.users = users;
        this.roomStatus = RoomStatus.WAIT;
        users.setUsersRoom(this);
    }

    public Room(String name) {
        this(name, new Users());
    }

    public void gameInitialize() throws IOException {
        ChatMessage chatMessage = new ChatMessage();
        setAttackCheckBoard(new boolean[Position.BOARD_SIZE][Position.BOARD_SIZE]);

        setRoomStatus(RoomStatus.PLAYING);
        getUsers().setUsersStatus(UserStatus.RUNNING);
        getUsers().setUsersIsInitialized(false);
        setAttackUserQueue(new LinkedList<>());
        setAttackPositionQueue(new LinkedList<>());

        chatMessage.setMessage("SYSTEM : 지렁이의 위치를 설정합니다.");
        send(chatMessage, objectMapper);

        chatMessage.setMessage("EVENT_INITIALIZE");
        send(chatMessage, objectMapper);
    }

    public void gameRun() throws IOException {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setMessage("SYSTEM : 모든 유저가 지렁이와 폭탄을 설정했습니다.");
        send(chatMessage, objectMapper);

        chatMessage.setMessage("EVENT_USERS_WORM_AND_BOMB_COUNT : " + getUsers().usersWormsAndBombCount());
        send(chatMessage, objectMapper);
    }

    public void send(ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(chatMessage.getMessage()));
//        for(WebSocketSession wss : sessions)
//            wss.sendMessage(textMessage);
        for (int i = 0; i < users.getSize(); i++) {
            if (users.getUsers().get(i).getUserStatus().equals(UserStatus.ESCAPE))
                continue;
            sessions.get(i).sendMessage(textMessage);
        }
    }

    public void send(int index, ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(chatMessage.getMessage()));
        sessions.get(index).sendMessage(textMessage);
    }

    public void attack() throws IOException {
        ChatMessage chatMessage = new ChatMessage();

        while(!attackUserQueue.isEmpty()) {
            User attackUser = attackUserQueue.remove();
            Position attackPosition = attackPositionQueue.remove();
            chatMessage.setMessage(attackUser.getName() + "의 공격 : " + attackPosition.getX() + ", " + attackPosition.getY());
            send(chatMessage, objectMapper);

            if (attackCheckBoard[attackPosition.getX()][attackPosition.getY()]) {
                chatMessage.setMessage("은 이미 다른 유저가 공격한 좌표이므로 무시됩니다.");
                send(chatMessage, objectMapper);
                continue;
            }
            attackCheckBoard[attackPosition.getX()][attackPosition.getY()] = true;

            //다른 유저들 폭탄이 있나 검사
            for (int i = 0; i < users.getSize(); i++) {
                if (users.getUsers().get(i).getUserStatus().equals(UserStatus.ESCAPE))
                    continue;
                if (users.getUsers().get(i).getBomb().getPosition().equals(attackPosition)) {
                    chatMessage.setMessage(
                            attackPosition.getX() + ", " + attackPosition.getY() + "에는 "
                                    + users.getUsers().get(i).getName() + "의 폭탄이 있었습니다.");
                    send(chatMessage, objectMapper);
                    users.getUsers().get(i).getBomb().setAlive(false);
                    int curx = attackPosition.getX();
                    int cury = attackPosition.getY();
                    int[] DX = {-1, -1, 0, 1, 1, 1, 0, -1};
                    int[] DY = {0, 1, 1, 1, 0, -1, -1, -1};
                    for (int k = 0; k < 8; k++) {
                        int nx = curx + DX[k];
                        int ny = cury + DY[k];
                        if (nx < 0 || nx >= Position.BOARD_SIZE || ny < 0 || ny >= Position.BOARD_SIZE)
                            continue;
                        attackUserQueue.add(users.getUsers().get(i));
                        attackPositionQueue.add(new Position(nx, ny));
                    }
                }
            }

            for (int i = 0; i < users.getSize(); i++) {
                if (users.getUsers().get(i).getUserStatus() != UserStatus.RUNNING)
                    continue;
                users.getUsers().get(i).getWorms().damage(attackPosition);
                if (users.getUsers().get(i).getWorms().remainWorms() == 0) {
                    users.getUsers().get(i).setUserStatus(UserStatus.LOSE);
                    chatMessage.setMessage(users.getUsers().get(i).getName() + "는 게임오버 되었습니다.");
                    send(chatMessage, objectMapper);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Position.BOARD_SIZE; i++)
            for (int j = 0; j < Position.BOARD_SIZE; j++)
                if (attackCheckBoard[i][j])
                    sb.append(1);
                else
                    sb.append(0);

        chatMessage.setMessage("EVENT_ATTACK_CHECK_BOARD : " + sb.toString());
        send(chatMessage, objectMapper);

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

    public Queue<User> getAttackUserQueue() {
        return attackUserQueue;
    }

    public void setAttackUserQueue(Queue<User> attackUserQueue) {
        this.attackUserQueue = attackUserQueue;
    }

    public Queue<Position> getAttackPositionQueue() {
        return attackPositionQueue;
    }

    public void setAttackPositionQueue(Queue<Position> attackPositionQueue) {
        this.attackPositionQueue = attackPositionQueue;
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

    public boolean isGameOver() {
        int remain = 0;
        for (int i = 0; i < users.getSize(); i++) {
            if (users.getUsers().get(i).getUserStatus().equals(UserStatus.ESCAPE))
                continue;
            if (users.getUsers().get(i).getWorms().remainWorms() > 0)
                remain++;
        }
        if (remain > 1)
            return false;
        return true;
    }

    public void gameEnd() throws IOException {
        List<User> userList = users.getUsers();
        List<User> winner = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage();

        for (User u : userList) {
            if (u.getUserStatus().equals(UserStatus.ESCAPE))
                continue;
            if (u.getLivingWormsCount() > 0) {
                u.setUserStatus(UserStatus.WIN);
                winner.add(u);
            }
        }

        if (winner.size() > 1) {
            throw new IllegalArgumentException("승자가 1명 초과 입니다");
        }
        chatMessage.setMessage("SYSTEM : 게임이 종료되었습니다.");
        send(chatMessage, objectMapper);
        if (winner.size() == 0)
            chatMessage.setMessage("SYSTEM : 승자가 없습니다(무승부).");
        else
            chatMessage.setMessage("SYSTEM : 승자는 " + winner.get(0).getName() + " 입니다.");
        send(chatMessage, objectMapper);

        for (int i = 0; i < users.getSize(); i++) {
            User u = users.getUsers().get(i);
            if (u.getUserStatus().equals(UserStatus.ESCAPE)) {
                u.setUserStatus(UserStatus.READY);
                removeUser(u);
                sessions.remove(i--);
            }
        }

        for (User u : userList)
            u.setUserStatus(UserStatus.READY);
        setRoomStatus(RoomStatus.WAIT);
        chatMessage.setMessage("EVENT_GAME_END");
        send(chatMessage, objectMapper);
        sendCurrentPlayer(objectMapper);
        chatMessage.setMessage("EVENT_USERS_WORM_AND_BOMB_COUNT : " + getUsers().usersWormsAndBombCount());
        send(chatMessage, objectMapper);
    }
}
