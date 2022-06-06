package com.capstonedesign07.wormgame;

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
public class WormsTests {

    @Test
    @DisplayName("Worms 생성 테스트")
    void createWormsTest() {
        Worm worm1 = new Worm(new Position(0, 0), 2);
        Worm worm2 = new Worm(new Position(1, 1), 2);
        Worm worm3 = new Worm(new Position(2, 2), 2);
        List<Worm> threeWorms = new ArrayList<>();
        threeWorms.add(worm1);
        threeWorms.add(worm2);
        threeWorms.add(worm3);
        Worms worms = new Worms(threeWorms, new char[Position.BOARD_SIZE][Position.BOARD_SIZE]);
        assertAll(
                () -> assertThat(worms.getWorms().get(0).getHead()).isEqualTo(new Position(0, 0)),
                () -> assertThat(worms.getWorms().get(1).isAlive()).isTrue(),
                () -> assertThat(worms.getWorms().get(2).getBody()[1]).isEqualTo(new Position(2, 4)),
                () -> assertThat(worms.getWorms()).hasSize(3)
        );
    }

    @Test
    @DisplayName("Worms 겹치는 지렁이 발생 테스트")
    void overlappedWormsTest() {
        Worm worm1 = new Worm(new Position(0, 0), 2);
        Worm worm2 = new Worm(new Position(1, 1), 2);
        Worm worm3 = new Worm(new Position(0, 2), 4);
        List<Worm> threeWorms = new ArrayList<>();
        threeWorms.add(worm1);
        threeWorms.add(worm2);
        threeWorms.add(worm3);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Worms(threeWorms, new char[Position.BOARD_SIZE][Position.BOARD_SIZE]));
    }
}
