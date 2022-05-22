package com.capstonedesign07.wormgame.service;

import com.capstonedesign07.wormgame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public String join(User user, HttpSession httpSession) {
//        validateDuplicateUser(user);
//        userRepository.save(user, httpSession);
//        return user.getSessionId();
//    }

//    private void validateDuplicateUser(User user) {
//        userRepository.findBySessionId(user.getName())
//                .ifPresent(u -> {
//                    throw new IllegalStateException("이미 존재하는 세션입니다.");
//                });
//    }

//    public List<User> findMembers() {
//        return userRepository.findAll();
//    }

//    public Optional<User> findOne(String userSessionId) {
//        return userRepository.findBySessionId(userSessionId);
//    }
}
