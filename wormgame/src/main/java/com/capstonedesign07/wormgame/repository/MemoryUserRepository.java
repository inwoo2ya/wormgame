package com.capstonedesign07.wormgame.repository;

import com.capstonedesign07.wormgame.domain.User;
import com.capstonedesign07.wormgame.domain.Users;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class MemoryUserRepository implements UserRepository {

    private final static Users store = new Users();

    @Override
    public User save(User user) {
        String sessionId = user.getSessionId();
        if (containsBySessionId(sessionId)) {
            delete(findBySessionId(sessionId));
        }
        store.addUser(user);
        return user;
    }

    @Override
    public User findBySessionId(String sessionId) {
        return store.findUserBySessionId(sessionId);
    }

    @Override
    public User findByName(String name) {
        return store.findUserByName(name);
    }

    @Override
    public Users findAll() {
        return store;
    }

    @Override
    public boolean containsBySessionId(String sessionId) {
        List<User> users = store.getUsers();
        for (User u : users) {
            if (u.getSessionId().equals(sessionId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void delete(User user) {
        store.removeUser(user);
    }
}
