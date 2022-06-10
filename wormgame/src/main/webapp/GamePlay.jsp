<!Doctype html>
<html lang="en">
    <head>
        <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <meta charset="UTF-8">
        <title>
            GameScreen
        </title>
        <link rel="stylesheet" href="./GamePlay.css">
        <script>
            sessionStorage.setItem("userName", '<%= session.getAttribute("userName") %>');
            sessionStorage.setItem("sessionId", '<%= session.getId() %>');
        </script>
    </head>
    <body>
    <div class="home">
        <div class="_label"></div>

        <div class="mainbox">
        <div class="roomhead">
           <p class="roomname" id="Roomname"><%= request.getAttribute("roomname") %></p><button class="roombtn" onclick="Roomname()">방 제목변경</button>
           </div>
            <div class="_label" style="height:10px"></div>
            <div class="leftbox">

                <div class="Gbox">
                    <div class="Game"id="Game">턴: </div>
                    <div id ="GBoard"></div>
                    </div>
                <div class="GRbox">
                    <ul class="repo"id="messageArea"></ul>
                </div>
                <!-- <form id="messageForm"> -->
                    <input class="in_" id="message" type="text" onkeypress="sendPressEnter(event)"/> <button onclick="send()" class="chatbtn">전송</button>
                <!-- </form> -->
                </div>
            <div class="rightbox">
                <div class="userbox">
                    <div class="playerbox"><p class="player">1p <p class="pln">&nbsp;<span id="playerName1"></span></p></p> <p class="worm"> 지렁이:&nbsp;<span id="wormnumber1"></span></p> <p class="boomb">폭탄:&nbsp;<span id="bombnumber1"></span></p></div>
                    <div class="playerbox"><p class="player">2p <p class="pln">&nbsp;<span id="playerName2"></span></p></p> <p class="worm"> 지렁이:&nbsp;<span id="wormnumber2"></span></p> <p class="boomb">폭탄:&nbsp;<span id="bombnumber2"></span></p></div>
                    <div class="playerbox"><p class="player">3p <p class="pln">&nbsp;<span id="playerName3"></span></p></p> <p class="worm"> 지렁이:&nbsp;<span id="wormnumber3"></span></p> <p class="boomb">폭탄:&nbsp;<span id="bombnumber3"></span></p></div>
                    <div class="playerbox"><p class="player">4p <p class="pln">&nbsp;<span id="playerName4"></span></p></p> <p class="worm"> 지렁이:&nbsp;<span id="wormnumber4"></span></p> <p class="boomb">폭탄:&nbsp;<span id="bombnumber4"></span></p></div>
                </div>
                <div class="btnarea">
                    <button class="startbtn" id="GameStartbtn" onclick="gameStartSend()"> 게임시작</button>
                    <button class="exitbtn" id="exit" onclick="location.href='findRoom'">나가기</button>
                </div>
            </div>
        </div>
        <div class="_label"></div>

    </div>
        <script src="webSocket.js"></script>
                <script>
                    window.onload = connect();
                </script>
        <script src="./GamePlay.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

    </body>
</html>
