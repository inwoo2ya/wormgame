'use strict';

var chatPage = document.querySelector('#chat-page'); //채팅 하는 곳
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message'); //메시지 입력칸
var messageArea = document.querySelector('#messageArea'); // 채팅 기록판
var connectingElement = document.querySelector('.connecting'); // 연결

var stompClient = null; //
var username = null; //user이름


function connect(event) {
    username = document.querySelector('#_username_id').value.trim();

    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/chatting');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + '님이 입장하셨습니다.';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + '님이 퇴장하셨습니다.';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

//usernameForm.addEventListener('submit', connect, true);
//messageForm.addEventListener('submit', sendMessage, true);

//=============GamePlayJS=================


const GameStart = document.getElementById("GameStartbtn");
GameStart.addEventListener("click",Play);

function Play(){

    makeboard(10,10);

    function makeboard(r,c){
        let tableEle = "<table>";

        for(let i = 0; i<c;i++){
            tableEle += '<tr>';
            for (let j = 0; j<r;j++){
                tableEle += '<td></td>';
            }
            tableEle +='</tr>';
        }
        tableEle += '</table>';
        console.log(tableEle);
        document.getElementById("Gboard").innerHTML = tableEle;
        }
    }
function Roomname(){
    var inputString = prompt('방 이름을 입력하세요!','방 제목');
    String(inputString);
    const title = document.getElementById("roomname");
    title.innerHTML = inputString;
}
