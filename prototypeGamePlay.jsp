<!Doctype html>
<html lang="en">
    <head>
        <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <meta charset="UTF-8">
        <title>
            GameScreen
        </title>
        <link rel="stylesheet" href="./GamePlay.css">

    </head>
    <body>
    <div class="home">
        <div class="_label"></div>

        <div class="roomhead">
           <p class="roomname" id="roomname">방 제목</p>
        </div> <button class="roombtn" onclick="roomname()">방 제목변경</button>
        <div class="mainbox">
            <div class="leftbox">

                <div class="Gbox">
                    <div class="Game"id="Game">턴: </div>
                     <button class="choiceBox"id="1a"></button>
                     <button class="choiceBox"id="2a"></button>
                     <button class="choiceBox"id="3a"></button>
                     <button class="choiceBox"id="4a"></button>
                     <button class="choiceBox"id="5a"></button>
                     <button class="choiceBox"id="6a"></button>
                     <button class="choiceBox"id="7a"></button>
                     <button class="choiceBox"id="8a"></button>
                     <button class="choiceBox"id="9a"></button>
                     <button class="choiceBox"id="10a"></button>
                     <button class="choiceBox"id="1b"></button>
                     <button class="choiceBox"id="2b"></button>
                     <button class="choiceBox"id="3b"></button>
                     <button class="choiceBox"id="4b"></button>
                     <button class="choiceBox"id="5b"></button>
                     <button class="choiceBox"id="6b"></button>
                     <button class="choiceBox"id="7b"></button>
                     <button class="choiceBox"id="8b"></button>
                     <button class="choiceBox"id="9b"></button>
                     <button class="choiceBox"id="10b"></button>
                     <button class="choiceBox"id="1c"></button>
                     <button class="choiceBox"id="2c"></button>
                     <button class="choiceBox"id="3c"></button>
                     <button class="choiceBox"id="4c"></button>
                     <button class="choiceBox"id="5c"></button>
                     <button class="choiceBox"id="6c"></button>
                     <button class="choiceBox"id="7c"></button>
                     <button class="choiceBox"id="8c"></button>
                     <button class="choiceBox"id="9c"></button>
                     <button class="choiceBox"id="10c"></button>
                     <button class="choiceBox"id="1d"></button>
                     <button class="choiceBox"id="2d"></button>
                     <button class="choiceBox"id="3d"></button>
                     <button class="choiceBox"id="4d"></button>
                     <button class="choiceBox"id="5d"></button>
                     <button class="choiceBox"id="6d"></button>
                     <button class="choiceBox"id="7d"></button>
                     <button class="choiceBox"id="8d"></button>
                     <button class="choiceBox"id="9d"></button>
                     <button class="choiceBox"id="10d"></button>
                     <button class="choiceBox"id="1e"></button>
                     <button class="choiceBox"id="2e"></button>
                     <button class="choiceBox"id="3e"></button>
                     <button class="choiceBox"id="4e"></button>
                     <button class="choiceBox"id="5e"></button>
                     <button class="choiceBox"id="6e"></button>
                     <button class="choiceBox"id="7e"></button>
                     <button class="choiceBox"id="8e"></button>
                     <button class="choiceBox"id="9e"></button>
                     <button class="choiceBox"id="10e"></button>
                     <button class="choiceBox"id="1f"></button>
                     <button class="choiceBox"id="2f"></button>
                     <button class="choiceBox"id="3f"></button>
                     <button class="choiceBox"id="4f"></button>
                     <button class="choiceBox"id="5f"></button>
                     <button class="choiceBox"id="6f"></button>
                     <button class="choiceBox"id="7f"></button>
                     <button class="choiceBox"id="8f"></button>
                     <button class="choiceBox"id="9f"></button>
                     <button class="choiceBox"id="10f"></button>
                     <button class="choiceBox"id="1g"></button>
                     <button class="choiceBox"id="2g"></button>
                     <button class="choiceBox"id="3g"></button>
                     <button class="choiceBox"id="4g"></button>
                     <button class="choiceBox"id="5g"></button>
                     <button class="choiceBox"id="6g"></button>
                     <button class="choiceBox"id="7g"></button>
                     <button class="choiceBox"id="8g"></button>
                     <button class="choiceBox"id="9g"></button>
                     <button class="choiceBox"id="10g"></button>
                     <button class="choiceBox"id="1h"></button>
                     <button class="choiceBox"id="2h"></button>
                     <button class="choiceBox"id="3h"></button>
                     <button class="choiceBox"id="4h"></button>
                     <button class="choiceBox"id="5h"></button>
                     <button class="choiceBox"id="6h"></button>
                     <button class="choiceBox"id="7h"></button>
                     <button class="choiceBox"id="8h"></button>
                     <button class="choiceBox"id="9h"></button>
                     <button class="choiceBox"id="10h"></button>
                     <button class="choiceBox"id="1i"></button>
                     <button class="choiceBox"id="2i"></button>
                     <button class="choiceBox"id="3i"></button>
                     <button class="choiceBox"id="4i"></button>
                     <button class="choiceBox"id="5i"></button>
                     <button class="choiceBox"id="6i"></button>
                     <button class="choiceBox"id="7i"></button>
                     <button class="choiceBox"id="8i"></button>
                     <button class="choiceBox"id="9i"></button>
                     <button class="choiceBox"id="10i"></button>
                     <button class="choiceBox"id="1j"></button>
                     <button class="choiceBox"id="2j"></button>
                     <button class="choiceBox"id="3j"></button>
                     <button class="choiceBox"id="4j"></button>
                     <button class="choiceBox"id="5j"></button>
                     <button class="choiceBox"id="6j"></button>
                     <button class="choiceBox"id="7j"></button>
                     <button class="choiceBox"id="8j"></button>
                     <button class="choiceBox"id="9j"></button>
                     <button class="choiceBox"id="10j"></button>

                    </div>
                <div class="GRbox">
                    <ul class="repo"id="messageArea"></ul>
                </div>
                <form id="messageForm">
                    <input class="in_" id="message" type="text"/> <button type='submit' class="chatbtn">전송</button>
                </form>
                </div>
            <div class="rightbox">
                <div class="userbox">
                    <div class="otherplayerbox"id="playername1">2p</div>
                    <div class="otherplayerbox"id="playername2">3p</div>
                    <div class="otherplayerbox"id="playername3">4p</div>
                </div>
                <div class="btnarea">
                    <button class="startbtn" id=""> 게임시작</button>
                    <button class="exitbtn" id="exit" onclick="back();">나가기</button>
                </div>
            </div>

        </div>
        <div class="_label"></div>
        <script src="./GamePlay.js"></script>
    </body>
</html>
