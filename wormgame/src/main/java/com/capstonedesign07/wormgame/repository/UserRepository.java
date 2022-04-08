package com.capstonedesign07.wormgame.repository;

import com.capstonedesign07.wormgame.domain.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user, HttpSession httpSession);
    Optional<User> findBySessionId(String sessionId);
    Optional<User> findByName(String name);
    List<User> findAll();
    void delete(User user);
}
