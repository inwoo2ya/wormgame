<!DOCTYPE html>
<html lang="en">

<head>
        <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="/socket.io/socket.io.js"></script>
        <title>Game</title>
        <style>
        .pop-layer .pop-container {
        padding: 20px 25px;
      }

      .pop-layer p.ctxt {
        color: #666;
        line-height: 25px;
      }

      .pop-layer .btn-r {
        width: 100%;
        margin: 10px 0 20px;
        padding-top: 10px;
        border-top: 1px solid #DDD;
        text-align: right;
      }

      .pop-layer {
        display: none;
        position: absolute;
        top: 50%;
        left: 50%;
        width: 410px;
        height: auto;
        background-color: #fff;
        border: 5px solid #3571B5;
        z-index: 10;
      }

      .dim-layer {
        display: none;
        position: fixed;
        _position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        z-index: 100;
      }

      .dim-layer .dimBg {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: #000;
        opacity: .5;
        filter: alpha(opacity=50);
      }

      .dim-layer .pop-layer {
        display: block;
      }


    .chartWrapper {
      position: relative;
    }

    .chartWrapper > canvas {
      position: absolute;
      left: 0;
      top: 0;
      pointer-events:none;
    }

    .chartAreaWrapper {
      height: 350px;
      width: 1200px;
      overflow-x: scroll;
    }

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
                <%--<form method="post" action="makeRoom">--%>

                     <div class="_label"></div>
                    <div class="divBox" style="border:none; text-align: left;" id="user_name">Player_Name:</div>
                    <div class="divBox margin_top60" style="border:none;">
                        <input class="_btn" type="submit" value="방만들기" name="submit" onclick="layer_popup('#layer2')">
                    </div>

                <form method ="post" action="SearchRoom">
                    <div class="divBox margin_top60" style="border:none;">
                        <input class="_btn" type="button" value="방찾기" name="submit">
                    </div>

                    <div class="divBox" style="margin-top:60px;border:none;">접속자 수 : <%= request.getAttribute("user_name") %></div>
                </form>
            </div>
        </div>
              <div class="dim-layer">
                <div class="dimBg"></div>
                <div id="layer2" class="pop-layer">
                  <div class="pop-container">
                    <div class="pop-conts">
                      <!--content //-->
                      <div id="popup_img" style="text-align: center">
                        <form method="post" action="createGame">
                          <input type="text" placeholder="방 이름 입력" id="roomname" />

                        <div class="row">
                          <label><input type="radio" name="num" value="2" checked /> 2인</label>
                          <label><input type="radio" name="num" value="3" /> 3인</label>
                          <label><input type="radio" name="num" value="4" /> 4인</label>
                        </div>
                      </div>
                      <div class="btn-r">
                        <div class="col-lg-6" style="float: left">
                          <input class="_createbtn" type="submit" value="생성" name="create">
                        </div>
                        <div class="col-lg-6" style="float: right">
                          <button type="button"><a href="javascript:void(0);" class="card bg-primary text-white btn btn-layerClose" style="text-decoration : none; color: black;">취소</a></button>
                        </div>
                        </form>
                      </div>
                      <!--// content-->
                    </div>
                  </div>
                </div>
              </div>
    </div>
    <script>
    var socket
    function layer_popup(el) {
        var $el = $(el) //레이어의 id를 $el 변수에 저장
        var isDim = $el.prev().hasClass("dimBg") //dimmed 레이어를 감지하기 위한 boolean 변수

        isDim ? $(".dim-layer").fadeIn() : $el.fadeIn()

        var $elWidth = ~~$el.outerWidth(),
          $elHeight = ~~$el.outerHeight(),
          docWidth = $(document).width(),
          docHeight = $(document).height()

        // 화면의 중앙에 레이어를 띄운다.
        if ($elHeight < docHeight || $elWidth < docWidth) {
          $el.css({
            marginTop: -$elHeight / 2,
            marginLeft: -$elWidth / 2,
          })
        } else {
          $el.css({ top: 0, left: 0 })
        }

        $el.find("a.btn-layerClose").click(function () {
          isDim ? $(".dim-layer").fadeOut() : $el.fadeOut() // 닫기 버튼을 클릭하면 레이어가 닫힌다.
          return false
        })

        $(".layer .dimBg").click(function () {
          $(".dim-layer").fadeOut()
          return false
        })
      }
    document.addEventListener("DOMContentLoaded", function(){
        <%--socket = io()
        socket.on("log", function (array) {
          console.log.apply(console, array)
        })

        socket.on("created", function (room) {
          console.log("Created room " + room)
          isInitiator = true
        })

        socket.on("full", function (room) {
          console.log("Room " + room + " is full")
        })

        socket.on("join", function (room) {
          console.log("Another peer made a request to join room " + room)
          console.log("This peer is the initiator of room " + room + "!")
          isChannelReady = true
        })

        socket.on("joined", function (room) {
          console.log("joined: " + room)
          isChannelReady = true
        })--%>

    	});

    </script>
    </body>
    </html>
