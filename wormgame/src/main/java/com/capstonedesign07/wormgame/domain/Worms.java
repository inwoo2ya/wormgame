package com.capstonedesign07.wormgame.domain;

import java.util.ArrayList;
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
            userBoard[head.getX()][head.getY()] = 'h';
            userBoard[body[0].getX()][body[0].getY()] = 'b';
            userBoard[body[1].getX()][body[1].getY()] = 'b';
        }
        this.worms = worms;
    }

    public Worms(String string, char[][] userBoard) {
        if (string.length() != 18) {
            throw new IllegalArgumentException("문자열의 길이가 지렁이 3마리 아님");
        }
        worms = new ArrayList<>();
        for (int i = 0 ; i < 18 ; i += 6) {
            Worm worm = new Worm(
                    new Position(string.charAt(i) - '0', string.charAt(i + 1) - '0'),
                    new Position(string.charAt(i + 2) - '0', string.charAt(i + 3) - '0'),
                    new Position(string.charAt(i + 4) - '0', string.charAt(i + 5) - '0'));
            Position head = worm.getHead();
            Position[] body = worm.getBody();
            if (userBoard[head.getX()][head.getY()] != 0) {
                throw new IllegalArgumentException("지렁이 머리 자리에 다른 객체가 있음");
            }
            if (userBoard[body[0].getX()][body[0].getY()] != 0 || userBoard[body[1].getX()][body[1].getY()] != 0) {
                throw new IllegalArgumentException("지렁이 몸통 자리에 다른 객체가 있음");
            }
            userBoard[head.getX()][head.getY()] = 'h';
            userBoard[body[0].getX()][body[0].getY()] = 'b';
            userBoard[body[1].getX()][body[1].getY()] = 'b';
            worms.add(worm);
        }
    }

    public void damage(Position position) {
        for (int i = 0; i < worms.size(); i++)
            worms.get(i).damage(position);
    }

    public int remainWorms() {
        int count = 0;
        for (int i = 0; i < worms.size(); i++)
            if (worms.get(i).isAlive())
                count++;
        return count;
    }

    public List<Worm> getWorms() {
        return worms;
    }
}
