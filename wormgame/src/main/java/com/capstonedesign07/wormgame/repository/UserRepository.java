package com.capstonedesign07.wormgame.repository;

import com.capstonedesign07.wormgame.domain.User;
import com.capstonedesign07.wormgame.domain.Users;

public interface UserRepository {

    User save(User user);
    User findBySessionId(String sessionId);
    User findByName(String name);
    Users findAll();
    void delete(User user);
}
