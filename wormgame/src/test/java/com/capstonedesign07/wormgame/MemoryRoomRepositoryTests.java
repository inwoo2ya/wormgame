package com.capstonedesign07.wormgame;

import com.capstonedesign07.wormgame.domain.Room;
import com.capstonedesign07.wormgame.repository.MemoryRoomRepository;
import com.capstonedesign07.wormgame.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class MemoryRoomRepositoryTests {

    @Test
    @DisplayName("MemoryRoomRepository 테스트")
    void memoryRoomRepositoryTest() {
        RoomRepository roomRepository = new MemoryRoomRepository();
        List<Room> rooms = roomRepository.getRooms();
        assertAll(
                () -> assertThat(rooms).hasSize(10),
                () -> assertThat(rooms.get(2).getName()).isEqualTo("임시 방제목 3")
        );
    }
}
