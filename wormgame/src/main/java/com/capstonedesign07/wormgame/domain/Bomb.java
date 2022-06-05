package com.capstonedesign07.wormgame.domain;

public class Bomb {

    private Position position;
    private boolean alive;

    public Bomb(Position position) {
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
