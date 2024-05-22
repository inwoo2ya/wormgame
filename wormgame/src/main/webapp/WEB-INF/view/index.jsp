<!DOCTYPE html>
<html lang="en">

<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>GGUMTEUL WARMS</title>
    <style>
        * {
            padding: 0;
            margin: 0;
        }
        html,
        body {
            width: 100%;
            height: 100%;
        }
        ._home {
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: center;
            background-repeat: no-repeat;
            background-image: url(img/bg.jpg);
            background-size: 100% 100%;
        }
        .flexCenter {
            text-align: center;
            margin: auto;
        }
        ._image {
            
            color:white;
            font-size:20pt;
            padding-top: 15px;
            padding-bottom: 20px;
            border-top-right-radius: 10px;
            border-top-left-radius: 10px;
            background-color: rgb(4, 174, 4);
            border:none;
            width: 60%;
            height: 30px;
        }
        ._from {
            width: 40%;
            height: 100%;
            background-color: white;
            border-bottom-left-radius: 10px;
            border-bottom-right-radius: 10px;
            box-shadow: 0px 0px 10px #ccc;
        }
        img {
            width: 100%;
            height: 100%;
        }
        .divBox {
            display: flex;
            justify-content: center;
            width: 80%;
            height: 46px;
            margin-top: 50px;
            margin-left: 10%;
            position: relative;
        }
        .warning{
            display: block;
            justify-content: center;
            position:relative;
        }
        ._label {
            display: flex;
            justify-content: center;
            width: 80%;
            height: 50px;
            /* border: 1px solid green; */
            margin-left: 10%;
            margin-bottom: 22px;
            margin-top: 10px;
        }
        ._username_img {
            width: 44px;
            height: 44px;
            position: absolute;
            top: 1px;
            left: 13.5px;
        }
        ._username_class {
            width: 80%;
            font-size:15pt;
            outline: none;
            padding-left: 50px;
        }
        .margin_top60 {
            margin-top: 40px;
        }
        ._btn {
            width: 100%;
            height: 88px;
            letter-spacing: 10px;
            font-size: 30px;
            background-color: rgb(4, 174, 4);
            border: none;
            outline: none;
            border-radius: 10px;
            color: white;
        }
        ._btn:hover {
            background-color: #044468;
            border: none;
            outline: none;
        }
    </style>
</head>

<body>
<div class="_home">

    <div class="flexCenter" style="min-height:390px;">
        <div class="_image" style="min-width: 500px;"> 꿈틀 웜즈
        </div>
        <div class="_from" style="min-width: 500px;">
            <form method="post" action="firstPost">

                 <div class="_label"style="margin-top:0"></div>
                <div class="_label" style="margin-bottom: 20px;"><span style="font-size:23px;width:420px">세션번호 : <%= session.getId() %></span></div>

                <div class="divBox">
                    <img class="_username_img"
                         src="img/username_mark.png">
                    <input class="_username_class"
                           type="text"
                           placeholder="닉네임"
                           id="_username_id"
                           name="_username_id"
                           maxlength="11">
                </div>
                <p class="warning" style="font-size:10pt;">닉네임은 최대 11글자입니다.</p>
                <div class="divBox" style="margin-top:30px; border:none;">
                    
                    <input class="_btn" type="submit" value="게임 시작" name="submit" id="GameStart">
                </div>

                <div class="divBox" style="border:none;"></div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
