<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Game</title>
    <script type="text/javascript" src="./GamePlay.js"></script>

    <style>
        *
        {
            padding: 0;
            margin: 0;
        }
        html, body
        {
            width: 100%;
            height: 100%;
        }
        ._home
        {
            width: 100%;
            height: 100%;
            background-repeat: no-repeat;
            background-image: url(warmbase.jpg);
            background-size: 100% 100%;
            background-attachment:fixed;
        }
        .inner
        {

            width: 1200px;
            margin: 0 auto;
            position: relative;
            overflow: hidden;
            height: 100%;
        }
        ._home .inner ul
        {
            margin-top: 80px;
            overflow: auto;
            list-style:none;
            height:80%;
        }
        ._home .inner ul li
        {
            border: 2px solid #000;
            width: 400px;
            float: left;
            margin-bottom:40px;
            margin-right:192px;
            padding:20px;
	    cursor: pointer;
            background-color: rgb(210, 253, 215);
        }
        ._home .inner ul li:nth-child(2n+2){ margin-right:0px; }
        .reday{ margin-top:30px;}
        .reday span:first-child i{ width:8px; height:8px; background:green; display:inline-block; margin-left:10px; border-radius:8px;  }
        .reday span:last-child{ float:right;}
        .reday.no span:first-child i{ background:red; }
        .backbtn{width:30px;height:30px;border-radius:5px;border-width:thin;display:inline-block;float:right; margin-top:3.5%;}
        .backbtn:hover{background-color: gray;}
    </style>
</head>
<body>
    <div class="_home">
        <div class="inner">
            <button class="backbtn" onclick="location.href = 'secondIndex';">x</button>
            <ul>
                <li onclick = "location.href = 'http://localhost:8080/GameRoom';">>
			<h3> <%= request.getAttribute("room0Name") %> </h3>
                    		<div class="reday">
                        		<p>
                            		<span>reday <i></i></span><span>1/4</span></p>
                    		</div>
			</button>
                </li>
                <li>
                    <h3> <%= request.getAttribute("room1Name") %> </h3>
                    <div class="reday">
                        <p>
                            <span>reday <i></i></span><span>1/4</span></p>
                    </div>
                </li>
                <li>
                    <h3> <%= request.getAttribute("room2Name") %> </h3>
                    <div class="reday">
                        <p>
                            <span>reday <i></i></span><span>1/4</span></p>
                    </div>
                </li>
                <li>
                    <h3> <%= request.getAttribute("room3Name") %> </h3>
                    <div class="reday no">
                        <p>
                            <span>playing <i></i></span><span>1/4</span></p>
                    </div>
                </li>
                <li>
                    <h3> <%= request.getAttribute("room4Name") %> </h3>
                    <div class="reday no">
                        <p>
                            <span>playing <i></i></span><span>1/4</span></p>
                    </div>
                </li>
                <li>
                    <h3> <%= request.getAttribute("room5Name") %> </h3>
                    <div class="reday">
                        <p>
                            <span>reday <i></i></span><span>1/4</span></p>
                    </div>
                </li>
                <li>
                    <h3> <%= request.getAttribute("room6Name") %> </h3>
                    <div class="reday">
                        <p>
                            <span>reday <i></i></span><span>1/4</span></p>
                    </div>
                </li>
                <li>
                    <h3> <%= request.getAttribute("room7Name") %> </h3>
                    <div class="reday">
                        <p>
                            <span>reday <i></i></span><span>1/4</span></p>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</body>
</html>
