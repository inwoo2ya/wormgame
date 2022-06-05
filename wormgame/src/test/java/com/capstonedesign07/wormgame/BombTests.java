package com.capstonedesign07.wormgame;

import com.capstonedesign07.wormgame.domain.Bomb;
import com.capstonedesign07.wormgame.domain.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BombTests {

    @Test
    @DisplayName("Bomb 생성 테스트")
    void createBombTest() {
        Bomb bomb = new Bomb(new Position(0, 0));
        Position bombPosition = bomb.getPosition();
        assertAll(
                () -> assertThat(bombPosition.getX()).isEqualTo(0),
                () -> assertThat(bombPosition.getY()).isEqualTo(0),
                () -> assertThat(bomb.isAlive()).isTrue()
        );
    }
}
