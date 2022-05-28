package com.capstonedesign07.wormgame;

import com.capstonedesign07.wormgame.domain.User;
import com.capstonedesign07.wormgame.repository.MemoryUserRepository;
import com.capstonedesign07.wormgame.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class MemoryUserRepositoryTests {

    @Test
    @DisplayName("MemoryUserRepository 테스트")
    void memoryUserRepositoryTest() {
        UserRepository userRepository = new MemoryUserRepository();
        User user1 = new User("SID1", "test1");
        User user2 = new User("SID2", "test2");
        userRepository.save(user1);
        userRepository.save(user2);
        assertAll(
                () -> assertThat(userRepository.getSize()).isEqualTo(2),
                () -> assertThat(userRepository.findBySessionId("SID2").getName()).isEqualTo("test2"),
                () -> assertThat(userRepository.findByName("test1").getSessionId()).isEqualTo("SID1"),
                () -> userRepository.delete(user1),
                () -> assertThat(userRepository.getSize()).isEqualTo(1)
        );
    }
}
