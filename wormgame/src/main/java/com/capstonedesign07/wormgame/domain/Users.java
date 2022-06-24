package com.capstonedesign07.wormgame.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Users {

    private final List<User> users;

    public Users(String iterativeSessionId, String iterativeUserName, int iteration) {
        users = IntStream.rangeClosed(1, iteration)
                .mapToObj(i -> new User(iterativeSessionId + i, iterativeUserName + i))
                .collect(Collectors.toList());
    }

    public Users(List<User> users) {
        this.users = users;
    }

    public Users() {
        users = new ArrayList<>();
    }

    public String usersWormsAndBombCount() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < getSize(); i++) {
            User user = users.get(i);
            if (user.getUserStatus().equals(UserStatus.ESCAPE)) {
                sb.append("--");
                continue;
            }

            sb.append(user.getLivingWormsCount());

            if (user.getBomb() == null) {
                sb.append(0);
                continue;
            }
            if (user.getBomb().getAlive())
                sb.append(1);
            else
                sb.append(0);
        }
        return sb.toString();
    }

    public int nonEscapeUsersCount() {
        int count = 0;
        for (User u : users) {
            if (u.getUserStatus().equals(UserStatus.ESCAPE))
                continue;
            count++;
        }
        return count;
    }

    public int getSize() {
        return users.size();
    }

    public User findUserByIndex(int index) {
        validateIndex(index);
        return users.get(index);
    }

    public User findUserBySessionId(String sessionId) {
        List<User> foundUser = IntStream.range(0, users.size())
                .filter(i -> users.get(i).getSessionId().equals(sessionId))
                .mapToObj(users::get)
                .collect(Collectors.toList());
        if(foundUser.size() != 1) {
            throw new IllegalArgumentException("findUserBySessionId : user가 여러명이거나 찾지 못함");
        }
        return foundUser.get(0);
    }

    public User findUserByName(String name) {
        List<User> foundUser = IntStream.range(0, users.size())
                .filter(i -> users.get(i).getName().equals(name))
                .mapToObj(users::get)
                .collect(Collectors.toList());
        if(foundUser.size() != 1) {
            throw new IllegalArgumentException("findUserByName : user가 여러명이거나 찾지 못함");
        }
        return foundUser.get(0);
    }

    public void setUsersStatus(UserStatus userStatus) {
        IntStream.range(0, users.size())
                .forEach(i -> users.get(i).setUserStatus(userStatus));
    }

    public void setUsersRoom(Room room) {
        IntStream.range(0, users.size())
                .forEach(i -> users.get(i).setRoom(room));
    }

    public void setUsersIsInitialized(boolean bool) {
        IntStream.range(0, users.size())
                .forEach(i -> users.get(i).setIsInitialized(bool));
    }

    public boolean isOnlyOneWinner() {
        return IntStream.range(0, users.size())
                .filter(i -> getUsers().get(i).getUserStatus().equals(UserStatus.WIN))
                .mapToObj(i -> getUsers().get(i))
                .collect(Collectors.toList())
                .size() == 1;
    }

    public void setUsersIsAttacked(boolean isAttacked) {
        IntStream.range(0, users.size())
                .forEach(i -> users.get(i).setIsAttacked(isAttacked));
    }

    public boolean turnAttackFinished() {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserStatus().equals(UserStatus.ESCAPE))
                continue;
            if (!users.get(i).getIsAttacked())
                return false;
        }
        return true;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    private void validateIndex(int index) {
        if(index >= users.size() || index < 0) {
            throw new IllegalArgumentException("findUserByIndex : 잘못된 index 접근");
        }
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(new ArrayList<>(users));
    }
}
