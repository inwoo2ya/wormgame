'use strict';

var chatPage = document.querySelector('#chat-page'); //채팅 하는 곳
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message'); //메시지 입력칸
var messageArea = document.querySelector('#messageArea'); // 채팅 기록판
var connectingElement = document.querySelector('.connecting'); // 연결

var stompClient = null; //
var username = null; //user이름

//usernameForm.addEventListener('submit', connect, true);
//messageForm.addEventListener('submit', sendMessage, true);

//=============GamePlayJS=================


// const GameStart = document.getElementById("GameStartbtn");
// GameStart.addEventListener("click",Play);

// function Play(){

//     makeboard(10,10);
//     btndisabled();
//     function btndisabled(){
//         const target = document.getElementById('GameStartbtn');
//         target.disabled = true;
//     }

//     function makeboard(r,c){
//         let tableEle = "<table>";

//         for(let i = 0; i<c;i++){
//             tableEle += '<tr>';
//             for (let j = 0; j<r;j++){
//                 tableEle += '<td onclick="onClick('+i +','+ j+')">'+i+','+j+'</td>'
//             }
//             tableEle +='</tr>';
//         }
//         tableEle += '</table>';
        
//         console.log(tableEle);
//         document.getElementById("Gboard").innerHTML = tableEle;
//         }
    
//     }
// function onClick(i,j){
//     console.log(i,j)
// }
function Roomname(){
    var inputString = prompt('방 이름을 입력하세요!','방 제목');
    String(inputString);
    const title = document.getElementById("Roomname");
    title.innerHTML = inputString;
}

function startBtnToggle(bool) { //게임 스타트 버튼 disable
    const target = document.getElementById('GameStartbtn');
    target.disabled = !bool;
}
function exitBtnToggle(bool){ // 나가기 버튼 disable
    const target = document.getElementById('exit');
    target.disabled = !bool;
}
