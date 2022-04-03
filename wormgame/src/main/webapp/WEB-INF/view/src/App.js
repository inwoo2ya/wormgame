import React,{useState} from 'react';
import React,{component} from 'react';
import logo from './logo.svg';
import './css/App.css';
import './css/prototype.css';

function App() {
  

  return (
      <div className='home'>
        <div className='_label'></div>
        <div className="mainbox">

          <LeftBox/>
          <Rightbox/>              


        </div>
        <div className="_label"></div>
        </div>
  );
}
function LeftBox(){
  return (
    <div className="leftbox">
                <div className="Gbox"> </div>
                <div className="GRbox"id="report">
                    <p className="repo"id="repo"></p>
                </div>
                    <input className="in_" id="chat" type="text"/> <button className="chatbtn" onclick="chatting();">전송</button> 
                </div>
  );
}

function Rightbox(){
  let [player,reset] = useState(['2p','3p','4p']);
  return (
  <div className="rightbox">
  <div className="userbox">
      <div className="otherplayerbox">{player[0]}</div>
      <div className="otherplayerbox">{player[1]}</div>
      <div className="otherplayerbox">{player[2]}</div>
  </div> 
  <div className="btnarea">
      <button className="startbtn" >게임시작</button>
      <button className="exitbtn" >나가기</button>
  </div>
  </div>
  );
}
export default App;
