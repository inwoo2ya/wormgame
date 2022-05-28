package com.capstonedesign07.wormgame;

import com.capstonedesign07.wormgame.domain.User;
import com.capstonedesign07.wormgame.domain.UserStatus;
import com.capstonedesign07.wormgame.domain.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class UsersTests {

    @Test
    @DisplayName("Users 생성 테스트")
    void createUsersTest() {
        Users users = new Users("SID", "test", 2);
        assertAll(
                () -> assertThat(users.findUserByIndex(0).getName()).isEqualTo("test1"),
                () -> assertThat(users.findUserByName("test2").getSessionId()).isEqualTo("SID2")
        );
    }

    @Test
    @DisplayName("중복 이름 검색 실패 테스트")
    void sameNameFindFailTest() {
        Users users = new Users(IntStream.rangeClosed(1, 2)
                .mapToObj(i -> new User("SID" + i, "test"))
                .collect(Collectors.toList()));
        assertThatIllegalArgumentException().
                isThrownBy(() -> users.findUserByName("test"));
    }

    @Test
    @DisplayName("Users 상태 변경 테스트")
    void usersStatusChangeTest() {
        Users users = new Users("SID", "test", 2);
        users.setUsersStatus(UserStatus.RUNNING);
        assertThat(users.findUserByIndex(1).getUserStatus()).isEqualByComparingTo(UserStatus.RUNNING);
    }
}
