package com.capstonedesign07.wormgame.repository;

import com.capstonedesign07.wormgame.domain.User;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;
import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {

    private static Map<String, User> store = new HashMap<>();

    @Override
    public User save(User user, HttpSession httpSession) {
        user.setSessionId(httpSession.getId());
        store.put(user.getSessionId(), user);
        return user;
    }

    @Override
    public Optional<User> findBySessionId(String sessionId) {
        return Optional.ofNullable(store.get(sessionId));
    }

    @Override
    public Optional<User> findByName(String name) {
        return store.values().stream()
                .filter(user -> user.getName().equals(name))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(User user) {

    }

    public void cleanStore() {
        store.clear();
    }
}
