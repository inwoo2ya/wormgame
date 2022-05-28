package com.capstonedesign07.wormgame;

import com.capstonedesign07.wormgame.domain.Room;
import com.capstonedesign07.wormgame.domain.RoomStatus;
import com.capstonedesign07.wormgame.domain.User;
import com.capstonedesign07.wormgame.domain.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class RoomTests {

    @Test
    @DisplayName("Room 생성 테스트")
    void createRoomTest() {
        Users users = new Users("SID", "test", 4);
        User user3 = new User("SID3", "test3");
        Room room = new Room("testRoom", users);
        assertAll(
                () -> assertThat(room.getRoomStatus()).isEqualByComparingTo(RoomStatus.WAIT),
                () -> assertThat(room.roomUsers()).hasSize(4),
                () -> assertThat(room.roomUsers().get(1).getName()).isEqualTo("test2"),
                () -> room.removeUser(user3),
                () -> assertThat(room.roomUsers()).hasSize(3)
        );
    }

    @Test
    @DisplayName("방 참가 테스트")
    void roomJoinTest() {
        Users users = new Users("SID", "test", 2);
        Room room = new Room("testRoom", users);
        User user3 = new User("SID3", "test3");
        room.addUser(user3);

        assertThat(room.roomUsers()).hasSize(3);
    }

    @Test
    @DisplayName("방 퇴장 테스트")
    void roomLeaveTest() {
        Users users = new Users("SID", "test", 2);
        User user2 = new User("SID2", "test2");

        Room room = new Room("testRoom", users);
        room.removeUser(user2);

        assertThat(room.roomUsers()).hasSize(1);
    }

    @Test
    @DisplayName("방 인원 초과 테스트")
    void roomSizeOverflowTest() {
        Users users = new Users("SID", "test", 4);
        Room room = new Room("testRoom", users);
        User user5 = new User("SID5", "test5");
        room.addUser(user5);

        List<User> roomUsers = room.roomUsers();
        assertAll(
                () -> assertThat(roomUsers).hasSize(4),
                () -> assertThat(roomUsers).doesNotContain(user5)
        );
    }
}
