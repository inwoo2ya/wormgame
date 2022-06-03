<!Doctype html>
<html lang="en">
    <head>
        <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <meta charset="UTF-8">
        <title>
            GameScreen
        </title>
        <link rel="stylesheet" href="./GamePlay.css">
        <script src="./GamePlay.js"></script>
        <script>
            window.onload = connect();
        </script>
    </head>
    <body>
    <div class="home">
        <div class="_label"></div>

        <div class="mainbox">
        <div class="roomhead">
           <p class="roomname" id="Roomname"> <%= request.getAttribute("roomname") %></p><button class="roombtn" onclick="Roomname()">방 제목변경</button>
           </div>
            <div class="_label" style="height:10px"></div>
            <div class="leftbox">

                <div class="Gbox">
                    <div class="Game"id="Game">턴: </div>
                    <div id ="Gboard"></div>
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
                    <div class="playerbox"><p>1p</p></div>
                    <div class="playerbox"><p>2p</p></div>
                    <div class="playerbox"><p>3p</p></div>
                    <div class="playerbox"><p>4p</p></div>
                </div>
                <div class="btnarea">
                    <button class="startbtn" id="GameStartbtn"> 게임시작</button>
                    <button class="exitbtn" id="exit" onclick="history.go(-1)">나가기</button>
                </div>
            </div>

        </div>
        <div class="_label"></div>
        <!-- <script src="./GamePlay.js"></script> -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
        <!-- <script src="/js/main.js"></script> -->
    </body>
</html>
