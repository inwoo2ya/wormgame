package com.capstonedesign07.wormgame.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Users {

    private List<User> users = new ArrayList<>();

    public User findUserByIndex(int index) {
        if(index >= users.size() || index < 0) {
            throw new IllegalArgumentException("findUserByIndex : 잘못된 index 접근");
        }
        return users.get(index);
    }

    public User findUserBySessionId(String sessionId) {
        List<User> foundUser = IntStream.range(0, users.size())
                .filter(i -> users.get(i).getName().equals(sessionId))
                .mapToObj(users::get)
                .collect(Collectors.toList());
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

    public void addUser(User user) {
        users.add(user);
    }
}
