<!DOCTYPE html>
<html lang="en">

<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Game</title>
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
            width: 60%;
            height: 100%;
        }
        ._from {
            width: 40%;
            height: 100%;
            background-color: white;
            border-radius: 10px;
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
            margin-top: 20px;
            margin-left: 10%;
            position: relative;
        }
        ._label {
            display: flex;
            justify-content: center;
            width: 80%;
            height: 60px;
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
            left: 1px;
        }
        ._username_class {
            width: 100%;
            outline: none;
            padding-left: 50px;
        }
        .margin_top60 {
            margin-top: 60px;
        }
        ._btn {
            width: 95%;
            height: 68px;
            letter-spacing: 10px;
            font-size: 25px;
            background-color: green;
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

    <div class="_container flexCenter" style="min-height:390px;">
        <div class="_image" style="min-width: 380px;">
        </div>
        <div class="_from" style="min-width: 380px;">
            <form method="post" action="makeRoom">

                 <div class="_label"></div>
                <div class="divBox" style="border:none; text-align: left;" id="user_name">Player_Name:</div>
                <div class="divBox margin_top60" style="border:none;">
                    <input class="_btn" type="submit" value="방만들기" name="submit">
                </div>

                <div class="divBox margin_top60" style="border:none;">
                    <input class="_btn" type="button" value="방찾기" name="submit">
                </div>

                <div class="divBox" style="margin-top:60px;border:none;">접속자 수 : 0</div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
