package com.capstonedesign07.wormgame;

import com.capstonedesign07.wormgame.domain.GameStatus;
import com.capstonedesign07.wormgame.domain.User;
import com.capstonedesign07.wormgame.domain.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WormgameApplicationTests {

	@Test
	@DisplayName("User 생성 테스트")
	void createUserTest() {
		User user = new User("SID", "test");
		assertAll(
				() -> assertThat(user.getSessionId()).isEqualTo("SID"),
				() -> assertThat(user.getName()).isEqualTo("test"),
				() -> assertThat(user.getGameStatus()).isEqualByComparingTo(GameStatus.READY)
		);
	}

	@Test
	@DisplayName("Users 생성 테스트")
	void createUsersTest() {
		Users users = new Users();
		User user1 = new User("SID1", "test1");
		User user2 = new User("SID2", "test2");
		users.addUser(user1);
		users.addUser(user2);
		assertAll(
				() -> assertThat(users.findUserByIndex(0).getName()).isEqualTo("test1"),
				() -> assertThat(users.findUserByName("test2").getSessionId()).isEqualTo("SID2")
		);
	}

	@Test
	@DisplayName("중복 이름 검색 실패 테스트")
	void sameNameFindFailTest() {
		Users users = new Users();
		User user1 = new User("SID1", "test");
		User user2 = new User("SID2", "test");
		users.addUser(user1);
		users.addUser(user2);
		assertThatIllegalArgumentException().
				isThrownBy(() -> users.findUserByName("test"));
	}
}
