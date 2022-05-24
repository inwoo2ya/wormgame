package com.capstonedesign07.wormgame;

import com.capstonedesign07.wormgame.domain.GameStatus;
import com.capstonedesign07.wormgame.domain.User;
import com.capstonedesign07.wormgame.domain.Users;
import com.capstonedesign07.wormgame.repository.MemoryUserRepository;
import com.capstonedesign07.wormgame.repository.UserRepository;
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

	@Test
	@DisplayName("MemoryUserRepository 테스트")
	void memoryUserRepositoryTest() {
		UserRepository userRepository = new MemoryUserRepository();
		User user1 = new User("SID1", "test1");
		User user2 = new User("SID2", "test2");
		userRepository.save(user1);
		userRepository.save(user2);
		assertAll(
				() -> assertThat(userRepository.findAll().getSize()).isEqualTo(2),
				() -> assertThat(userRepository.findBySessionId("SID2").getName()).isEqualTo("test2"),
				() -> assertThat(userRepository.findByName("test1").getSessionId()).isEqualTo("SID1"),
				() -> userRepository.delete(user1),
				() -> assertThat(userRepository.findAll().getSize()).isEqualTo(1)
		);
	}
}
