package com.capstonedesign07.wormgame.domain;

public class Bomb {

    private Position position;
    private boolean alive;

    public Bomb(Position position, char[][] userBoard) {
        if (userBoard[position.getX()][position.getY()] != 0) {
            throw new IllegalArgumentException("폭탄 위치에 다른 객체가 있음");
        }
        this.position = position;
        this.alive = true;
    }

    public boolean isAlive() {
        return alive;
    }

    public Position getPosition() {
        return position;
    }
}
