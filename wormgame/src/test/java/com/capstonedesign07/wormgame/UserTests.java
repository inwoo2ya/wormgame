package com.capstonedesign07.wormgame;

import com.capstonedesign07.wormgame.domain.User;
import com.capstonedesign07.wormgame.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class UserTests {

    @Test
    @DisplayName("User 생성 테스트")
    void createUserTest() {
        User user = new User("SID", "test");
        assertAll(
                () -> assertThat(user.getSessionId()).isEqualTo("SID"),
                () -> assertThat(user.getName()).isEqualTo("test"),
                () -> assertThat(user.getUserStatus()).isEqualByComparingTo(UserStatus.READY)
        );
    }

    @Test
    @DisplayName("User 동등성 테스트")
    void userEqualityTest() {
        User user1 = new User("SID", "test");
        User user2 = new User("SID", "test");
        User user3 = new User("SID", "test2");
        assertAll(
                () -> assertThat(user1).isEqualTo(user2),
                () -> assertThat(user2).isNotEqualTo(user3)
        );
    }

    @Test
    @DisplayName("User 상태 변경 테스트")
    void userStatusChangeTest() {
        User user = new User("SID", "test");
        user.setUserStatus(UserStatus.RUNNING);
        assertThat(user.getUserStatus()).isEqualByComparingTo(UserStatus.RUNNING);
    }
}
