package com.capstonedesign07.wormgame;

import com.capstonedesign07.wormgame.domain.GameStatus;
import com.capstonedesign07.wormgame.domain.User;
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

}
