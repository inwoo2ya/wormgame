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
        Worm worm1 = new Worm(new Position(0, 0), 2);
        Worm worm2 = new Worm(new Position(0, 0), new Position(2, 2));
        Position headPosition = new Position(0, 0);
        Position bodyPosition = new Position(0, 1);
        assertAll(
                () -> assertThat(worm1.getHead()).isEqualTo(headPosition),
                () -> assertThat(worm1.getBody()[0]).isEqualTo(bodyPosition),
                () -> assertThat(worm1.isAlive()).isTrue(),
                () -> assertThat(worm2.getBody()[0]).isEqualTo(new Position(1, 1))
        );
    }

    @Test
    @DisplayName("Worm이 보드를 벗어나는 경우 테스트")
    void wormOutOfBoundsTest() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Worm(new Position(0, 0), 0));
    }

    @Test
    @DisplayName("Worm의 머리와 몸통이 1좌표 차이날 때 테스트")
    void wormHeadTailDistanceTest() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Worm(new Position(0, 0), new Position(1, 1)));
    }
}
