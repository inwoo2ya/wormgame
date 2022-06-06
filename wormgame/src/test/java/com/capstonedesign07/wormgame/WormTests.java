package com.capstonedesign07.wormgame;

import com.capstonedesign07.wormgame.domain.Position;
import com.capstonedesign07.wormgame.domain.Worm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WormTests {

    @Test
    @DisplayName("Worm 생성 테스트")
    void createWormTest() {
        Worm worm = new Worm(new Position(0, 0), 2);
        Position headPosition = new Position(0, 0);
        Position bodyPosition = new Position(0, 1);
        assertAll(
                () -> assertThat(worm.getHead()).isEqualTo(headPosition),
                () -> assertThat(worm.getBody()[0]).isEqualTo(bodyPosition),
                () -> assertThat(worm.isAlive()).isTrue()
        );
    }

    @Test
    @DisplayName("Worm이 보드를 벗어나는 경우 테스트")
    void wormOutOfBoundsTest() {
        assertThatIllegalArgumentException().
                isThrownBy(() -> new Worm(new Position(0, 0), 0));
    }
}
