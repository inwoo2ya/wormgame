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
            margin-bottom:50px;
            margin-right:192px;
            padding:20px;
	    cursor: pointer;
            background-color: rgb(210, 253, 215);
        }
        ._home .inner ul li:nth-child(2n+2){ margin-right:0px; }
        .ready{ margin-top:30px;}
        .ready span:first-child i{ width:8px; height:8px; background:green; display:inline-block; margin-left:10px; border-radius:8px;  }
        .ready span:last-child{ float:right;}
        .ready.no span:first-child i{ background:red; }
        .backbtn{width:30px;height:30px;border-radius:5px;border-width:thin;display:inline-block;float:right; margin-top:3.5%;}
        .backbtn:hover{background-color: gray;}
    </style>
</head>
<body>
    <div class="_home">
        <div class="inner">
            <button class="backbtn" onclick="location.href = 'secondIndex';">x</button>
            <ul>
                <li onclick = "location.href = 'joinRoom?roomNumber=0';">
			<h3> <%= request.getAttribute("room0Name") %> </h3>
                    		<div class="ready">
                        		<p>
                            		<span>ready <i></i></span><span><%= request.getAttribute("room0UsersSize") %>/4</span></p>
                    		</div>
			</button>
                </li>
                <li onclick = "location.href = 'joinRoom?roomNumber=1';">
                    <h3> <%= request.getAttribute("room1Name") %> </h3>
                    <div class="ready">
                        <p>
                            <span>ready <i></i></span><span><%= request.getAttribute("room1UsersSize") %>/4</span></p>
                    </div>
                </li>
                <li onclick = "location.href = 'joinRoom?roomNumber=2';">
                    <h3> <%= request.getAttribute("room2Name") %> </h3>
                    <div class="ready">
                        <p>
                            <span>ready <i></i></span><span><%= request.getAttribute("room2UsersSize") %>/4</span></p>
                    </div>
                </li>
                <li onclick = "location.href = 'joinRoom?roomNumber=3';">
                    <h3> <%= request.getAttribute("room3Name") %> </h3>
                    <div class="ready">
                        <p>
                            <span>ready <i></i></span><span><%= request.getAttribute("room3UsersSize") %>/4</span></p>
                    </div>
                </li>
                <li onclick = "location.href = 'joinRoom?roomNumber=4';">
                    <h3> <%= request.getAttribute("room4Name") %> </h3>
                    <div class="ready">
                        <p>
                            <span>ready<i></i></span><span><%= request.getAttribute("room4UsersSize") %>/4</span></p>
                    </div>
                </li>
                <li onclick = "location.href = 'joinRoom?roomNumber=5';">
                    <h3> <%= request.getAttribute("room5Name") %> </h3>
                    <div class="ready">
                        <p>
                            <span>ready <i></i></span><span><%= request.getAttribute("room5UsersSize") %>/4</span></p>
                    </div>
                </li>
                <li onclick = "location.href = 'joinRoom?roomNumber=6';">
                    <h3> <%= request.getAttribute("room6Name") %> </h3>
                    <div class="ready">
                        <p>
                            <span>ready <i></i></span><span><%= request.getAttribute("room6UsersSize") %>/4</span></p>
                    </div>
                </li>
                <li onclick = "location.href = 'joinRoom?roomNumber=7';">
                    <h3> <%= request.getAttribute("room7Name") %> </h3>
                    <div class="ready">
                        <p>
                            <span>ready <i></i></span><span><%= request.getAttribute("room7UsersSize") %>/4</span></p>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</body>
</html>
