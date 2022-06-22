package com.capstonedesign07.wormgame;

import com.capstonedesign07.wormgame.domain.Bomb;
import com.capstonedesign07.wormgame.domain.Position;
import com.capstonedesign07.wormgame.domain.Worm;
import com.capstonedesign07.wormgame.domain.Worms;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BombTests {

    @Test
    @DisplayName("Bomb 생성 테스트")
    void createBombTest() {
        Bomb bomb = new Bomb(new Position(0, 0), new char[Position.BOARD_SIZE][Position.BOARD_SIZE]);
        Position bombPosition = bomb.getPosition();
        assertAll(
                () -> assertThat(bombPosition.getX()).isEqualTo(0),
                () -> assertThat(bombPosition.getY()).isEqualTo(0),
                () -> assertThat(bomb.getAlive()).isTrue()
        );
    }

    @Test
    @DisplayName("지렁이가 Bomb와 위치가 겹치는 경우 테스트")
    void bombOverlappedWithWormTest() {
        char[][] userBoard = new char[Position.BOARD_SIZE][Position.BOARD_SIZE];
        Worm worm1 = new Worm(new Position(0, 0), 2);
        Worm worm2 = new Worm(new Position(1, 1), 2);
        Worm worm3 = new Worm(new Position(2, 2), 2);
        List<Worm> threeWorms = new ArrayList<>();
        threeWorms.add(worm1);
        threeWorms.add(worm2);
        threeWorms.add(worm3);
        Worms worms = new Worms(threeWorms, userBoard);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Bomb(new Position(2, 2), userBoard));
    }
}
