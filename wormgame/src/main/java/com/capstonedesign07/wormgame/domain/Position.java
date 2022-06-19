package com.capstonedesign07.wormgame.domain;

public class Position {

    public final static int BOARD_SIZE = 10;
    private int x;
    private int y;

    public Position(int x, int y) {
        if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) {
            throw new IllegalArgumentException("위치 객체의 좌표가 보드 크기를 벗어남");
        }
        this.x = x;
        this.y = y;
    }

    public Position(String xy) {
        this(Character.getNumericValue(xy.charAt(0)), Character.getNumericValue(xy.charAt(1)));
    }

    @Override
    public boolean equals(final Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position)o;
        return Integer.compare(x, position.x) == 0 && Integer.compare(y, position.y) == 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
