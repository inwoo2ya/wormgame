package com.capstonedesign07.wormgame;

import com.capstonedesign07.wormgame.domain.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PositionTests {

    @Test
    @DisplayName("Position 생성 테스트")
    void createPositionTest() {
        Position position = new Position(0, 0);
        assertAll(
                () -> assertThat(position.getX()).isEqualTo(0),
                () -> assertThat(position.getY()).isEqualTo(0)
        );
    }

    @Test
    @DisplayName("Position 동등성 테스트")
    void positionEqualityTest() {
        Position position1 = new Position(0, 0);
        Position position2 = new Position(0, 0);
        Position position3 = new Position(0, 1);
        assertAll(
                () -> assertThat(position1).isEqualTo(position2),
                () -> assertThat(position1).isNotEqualTo(position3)
        );
    }

    @Test
    @DisplayName("Position 보드 사이즈 초과 테스트")
    void positionBoardSizeOutOfBoundsTest() {
        assertAll(
                () -> assertThatIllegalArgumentException()
                        .isThrownBy(() -> new Position(-1, 0)),
                () -> assertThatIllegalArgumentException()
                        .isThrownBy(() -> new Position(10, 0))
        );
    }
}
