package com.capstonedesign07.wormgame;

import com.capstonedesign07.wormgame.domain.Room;
import com.capstonedesign07.wormgame.domain.RoomStatus;
import com.capstonedesign07.wormgame.domain.User;
import com.capstonedesign07.wormgame.domain.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class RoomTests {

    @Test
    @DisplayName("Room 생성 테스트")
    void createRoomTest() {
        Users users = new Users();
        User user1 = new User("SID1", "test1");
        User user2 = new User("SID2", "test2");
        User user3 = new User("SID3", "test3");
        User user4 = new User("SID4", "test4");
        users.addUser(user1);
        users.addUser(user2);
        users.addUser(user3);
        users.addUser(user4);

        Room room = new Room("testRoom", users);
        assertAll(
                () -> assertThat(room.getRoomStatus()).isEqualByComparingTo(RoomStatus.WAIT),
                () -> assertThat(room.roomUsers()).hasSize(4),
                () -> assertThat(room.roomUsers().get(1).getName()).isEqualTo("test2"),
                () -> room.removeUser(user3),
                () -> assertThat(room.roomUsers()).hasSize(3)
        );
    }
}
