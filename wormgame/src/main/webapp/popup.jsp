<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <title>Window Popup</title>
    <style>
        *{padding: 0; margin: 0;}
        
        #pop_container{
            font-family: 'NotoSans','맑은 고딕', 'Malgun Gothic',"돋움", dotum, arial, sans-serif;
            text-align: center;
            position: relative;
        }
            .top {
                position: relative;
                display: flex; 
                justify-content: space-between;
                padding: 0.5rem 1.4rem;
                background-color: #7afd00;
                vertical-align: middle;
            }
                h1.infoTit {
                    font-size: 20px; 
                    color:#ffffff;
                }
            main.textBox{
                padding-top: 3.4em;
                text-align: center;
            }
                h2.tit{
                    font-size: 1.6em;
                    letter-spacing: -2px;
                    font-weight: bold;
                    line-height: 1.5em;
                }
                p {
                    line-height: 1.8em;
                }
                p.textContents{
                    margin: 1.5em 1.8em;
                    font-size: 1.1em;
                    font-weight: 200;
                }
 
            footer {    
                position: absolute;
                bottom: 0;
                width: 100%;
                background-color: #7afd00;
            }
            footer.btnBox_footer {
                padding: 0.5rem 0 0.7rem;
                display: flex;
                justify-content: flex-end;
            }
                
    </style>
</head>
<body>
    <div id="popup">
        <header class="top"> 
            <h1 class="infoTit">전투장 개설</h1> 
        </header>
        <main class="textBox">
            <h2 class="tit">전투 인원</h2>
            <p class="textContents">

                <!--인원수 선택-->
                <input type="radio" name="person" value="person2" id="person2"> 2
                <input type="radio" name="person" value="person3" id="person3"> 3
                <input type="radio" name="person" value="person3" id="person4"> 4<br>

                <!--방제목 설정-->
                <input type="text" name="room_name" placeholder="임시 방제목" id="room_name"> <br>


                <!--닫기 및 방만들기-->
                <script type="text/javascript">
                    function createGame(){
                        window.open('GamePlay.jsp'); // 우선 어떻게 넘어가는지 자바스크립트로 설명
                        window.close();
                    }
                </script>
                <!--전투장 개설 버튼 누르면 index.jsp는 게임방(GamePlay.jsp)으로 넘어가고 팝업창은 닫힌다.-->
                <input type="button" value="전투장 개설"  id="room_open" onclick="createGame()" />
                <input type="button" value="닫 기" id="close" onclick="self.close();" />

            </p>
        </main>
        
    </div>   
    <footer class="btnBox_footer">
        Worm Game
        </form>
    </footer>
 <scriptS type="text/javascript"src="./GamePlay.js"></script>
</body>
</html>