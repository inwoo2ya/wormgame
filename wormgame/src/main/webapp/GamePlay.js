'use strict';

var chatPage = document.querySelector('#chat-page'); //채팅 하는 곳
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message'); //메시지 입력칸
var messageArea = document.querySelector('#messageArea'); // 채팅 기록판
var connectingElement = document.querySelector('.connecting'); // 연결

var stompClient = null; //
var username = null; //user이름

var websocket;

function connect() {
    websocket = new WebSocket("ws://localhost:8080/chatHandler");
    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onclose = onClose;
}

function disconnect() {
    msg = document.getElementById("nickname").value;
    // websocket.send(msg + "님이 퇴장하셨습니다");
    websocket.send(JSON.stringify({chatRoomName : "임시 방제목 1", messageType : "LEAVE", writer: nickname}))
    websocket.close();
}

function send() {
    // nickname = document.getElementById("nickname").value;
    nickname = "temp";
    msg = document.getElementById("message").value;
    // websocket.send(nickname + ":" + msg);
    websocket.send(JSON.stringify({chatRoomName : "임시 방제목 1", messageType : "CHAT", writer : nickname, message : msg}));
    document.getElementById("message").value = "";
}

function onOpen() {
    // nickname = document.getElementById("nickname").value;
    var nickname = "temp";
    // two = document.getElementById("two");
    // two.style.display = 'block';
    // websocket.send(nickname + "님 입장하셨습니다.");
    websocket.send(JSON.stringify({chatRoomName : "임시 방제목 1", messageType : "ENTER", writer : nickname}));
}

function onMessage(evt) {
    var data = evt.data;
    var chatarea = document.getElementById("messageArea");
    chatarea.innerHTML = data + "<br/>" + chatarea.innerHTML
}

function onClose() {
}

//usernameForm.addEventListener('submit', connect, true);
//messageForm.addEventListener('submit', sendMessage, true);

//=============GamePlayJS=================


const GameStart = document.getElementById("GameStartbtn");
GameStart.addEventListener("click",Play);

function Play(){

    makeboard(10,10);
    btndisabled();
    function btndisabled(){
        const target = document.getElementById('GameStartbtn');
        target.disabled = true;
    }

    function makeboard(r,c){
        let tableEle = "<table>";

        for(let i = 0; i<c;i++){
            tableEle += '<tr>';
            for (let j = 0; j<r;j++){
                tableEle += '<td onclick="onClick('+i +','+ j+')">'+i+','+j+'</td>'
            }
            tableEle +='</tr>';
        }
        tableEle += '</table>';
        
        console.log(tableEle);
        document.getElementById("Gboard").innerHTML = tableEle;
        }
    
    }
function onClick(i,j){
    console.log(i,j)
}
function Roomname(){
    var inputString = prompt('방 이름을 입력하세요!','방 제목');
    String(inputString);
    const title = document.getElementById("Roomname");
    title.innerHTML = inputString;
}

