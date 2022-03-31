<!Doctype html>
<html lang="en">
    <head>
        <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title>
            GameScreen
        </title>
        <link rel="stylesheet" href="./Game.css">   <!-- 로드 안됨? -->
    </head>
    <body>
    <div class="home">
        <div class="_label"></div>
        <div class="player">
            player1
        </div>
        <div class="mainbox">
            <div class="leftbox">
                <div class="Gbox">
                    player 게임화면
                </div>
            </div>
            <div class="rightbox">
                <div class="GRbox">
                    게임 기록 <br>턴1.<br>ex."player1"이 (x,y) 좌표를 공격했습니다.<br>ex."player1"의 지렁이 HP-1
                </div>
                <div class="playerbox">
                    플레이어 상황(지렁이 생존수,지렁이hp,폭탄 수)<br>ex. player1 생존 수: 3, 지렁이 HP: 5, 폭탄 수: 1
                </div>

            </div>
        </div>
        <div class="_label"></div>
    </div>
    </body>
</html>
