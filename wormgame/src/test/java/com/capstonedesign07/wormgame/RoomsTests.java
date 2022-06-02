package com.capstonedesign07.wormgame;

import com.capstonedesign07.wormgame.domain.Room;
import com.capstonedesign07.wormgame.domain.Rooms;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class RoomsTests {

    @Test
    @DisplayName("Rooms 생성 테스트")
    void createRoomsTest() {
        Rooms rooms = new Rooms("test Room Name");
        assertAll(
                () -> assertThat(rooms.getRooms()).hasSize(8),
                () -> assertThat(rooms.findRoomByIndex(1).getName()).isEqualTo("test Room Name 2")
        );
    }

    @Test
    @DisplayName("Rooms 이름으로 찾기 테스트")
    void findRoomByNameTest() {
        Rooms rooms = new Rooms("test Room Name");
        Room room5 = rooms.getRooms().get(4);
        assertThat(rooms.findRoomByName("test Room Name 5")).isEqualTo(room5);
    }
}
