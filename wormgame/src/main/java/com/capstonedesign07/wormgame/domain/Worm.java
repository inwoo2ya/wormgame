package com.capstonedesign07.wormgame.domain;

public class Worm {

    private final static int VECTOR_SIZE = 8;
    private final static int[] DX = {-1, -1, 0, 1, 1, 1, 0, -1};
    private final static int[] DY = {0, 1, 1, 1, 0, -1, -1, -1};

    private Position head;
    private Position[] body = new Position[2];
    private int healthPoint;

    public Worm(Position head, int vector) {
        if (vector < 0 || vector >= VECTOR_SIZE) {
            throw new IllegalArgumentException("8방향이 아님");
        }
        int tailX = head.getX() + 2 * DX[vector];
        int tailY = head.getY() + 2 * DY[vector];
        if(tailX < 0 || tailX >= Position.BOARD_SIZE || tailY < 0 || tailY >= Position.BOARD_SIZE) {
            throw new IllegalArgumentException("몸통이 보드를 벗어남");
        }
        this.head = head;
        body[0] = new Position(head.getX() + DX[vector], head.getY() + DY[vector]);
        body[1] = new Position(body[0].getX() + DX[vector], body[0].getY() + DY[vector]);
        this.healthPoint = 2;
    }
    public Worm(Position head, Position tail) {
        if ((head.getX() + tail.getX()) % 2 == 1 || (head.getY() + tail.getY()) % 2 == 1) {
            throw new IllegalArgumentException("몸통의 좌표가 정수가 아님");
        }
        this.head = head;
        body[0] = new Position((head.getX() + tail.getX()) / 2, (head.getY() + tail.getY()) / 2);
        body[1] = tail;
        this.healthPoint = 2;
    }

    public Worm(Position head, Position body, Position tail) {
        this.head = head;
        this.body[0] = body;
        this.body[1] = tail;
        this.healthPoint = 2;
    }

    public void damage(Position position) {
        if (position.equals(head))
            healthPoint = 0;
        if (position.equals(body[0]) || position.equals(body[1]))
            healthPoint--;
    }

    public boolean isAlive() {
        return healthPoint > 0;
    }

    public Position getHead() {
        return head;
    }

    public Position[] getBody() {
        return body;
    }
}
