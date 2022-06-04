package com.capstonedesign07.wormgame.domain;

public class Game {

    public static void run(Room room) {
        room.setRoomStatus(RoomStatus.PLAYING);
        room.getUsers().setUsersStatus(UserStatus.RUNNING);
        //백엔드 게임로직
    }
}
