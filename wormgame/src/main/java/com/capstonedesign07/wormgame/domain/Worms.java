package com.capstonedesign07.wormgame.domain;

import java.util.List;

public class Worms {

    private final static int WORMS_COUNT = 3;
    private final List<Worm> worms;

    public Worms(List<Worm> worms, char[][] userBoard) {
        if (worms.size() != WORMS_COUNT) {
            throw new IllegalArgumentException("지렁이가 3마리가 아님");
        }
        boolean board[][] = new boolean[Position.BOARD_SIZE][Position.BOARD_SIZE];
        for (int i = 0; i < WORMS_COUNT; i++) {
            Worm worm = worms.get(i);
            Position head = worm.getHead();
            Position[] body = worm.getBody();
            if (userBoard[head.getX()][head.getY()] != 0) {
                throw new IllegalArgumentException("지렁이 머리 자리에 다른 객체가 있음");
            }
            if (userBoard[body[0].getX()][body[0].getY()] != 0 || userBoard[body[1].getX()][body[1].getY()] != 0) {
                throw new IllegalArgumentException("지렁이 몸통 자리에 다른 객체가 있음");
            }
            userBoard[head.getX()][head.getY()] = 'H';
            userBoard[body[0].getX()][body[0].getY()] = 'B';
            userBoard[body[1].getX()][body[1].getY()] = 'B';
        }
        this.worms = worms;
    }

    public List<Worm> getWorms() {
        return worms;
    }
}
