package com.capstonedesign07.wormgame.controller;

import com.capstonedesign07.wormgame.domain.Room;
import com.capstonedesign07.wormgame.domain.User;
import com.capstonedesign07.wormgame.repository.MemoryRoomRepository;
import com.capstonedesign07.wormgame.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;

@Controller
public class WebController {

    int tempRoomCount = 0;
    @Autowired private HttpSession httpSession;
    @Autowired private HttpServletRequest httpServletRequest;
    private RoomRepository roomRepository;

    @Autowired
    WebController(MemoryRoomRepository memoryRoomRepository) {
        this.roomRepository = memoryRoomRepository;
    }

    @GetMapping("")
    public String root() {
        System.out.println("httpSession.getId() = " + httpSession.getId());
        return "index.jsp";
    }

    @GetMapping("index")
    public String index() {
        return "index.jsp";
    }
    
    @GetMapping("Game")
    public String Game() {
        return "Game.jsp";
    }
    
    @GetMapping("GamePlay")
    public String GamePlay() {
        return "GamePlay.jsp";
    }

    @GetMapping("sessionPrint")
    public String sessionPrint() {
        Enumeration<String> e = httpSession.getAttributeNames();
        System.out.println("method call : sessionPrint()");
        while(e.hasMoreElements()) {
            String sessionAttribute = (String)e.nextElement();
            System.out.println("session.getAttribute(" + sessionAttribute + ") : " + httpSession.getAttribute(sessionAttribute));
        }
        return "index.jsp";
    }

    @GetMapping("joinTest")
    public String joinTest() {
        User user = new User();
        user.setSessionId(httpSession.getId());
        httpSession.setAttribute(user.getSessionId(), user);
        return "index.jsp";
    }

    @PostMapping("join")
    @ResponseBody
    public String join() {
        User user = new User();
        user.setSessionId(httpSession.getId());
        System.out.println("httpSession.getId() = " + httpSession.getId());
        user.setName(httpServletRequest.getParameter("_username_id"));
        System.out.println("httpServletRequest.getParameter(\"_username_id\") = " + httpServletRequest.getParameter("_username_id"));
        httpSession.setAttribute(user.getSessionId(), user);
        return "sessionId : " + user.getSessionId() + "<br/>userName : " + user.getName();
    }

    @GetMapping("makeTest")
    @ResponseBody
    public String make() {
        String s = "";

        User user = new User();
        user.setSessionId(httpSession.getId());
        user.setName(httpServletRequest.getParameter("_username_id"));

        Room room = new Room();
        room.setName(user.getSessionId());
        room.setRoomSize(2);
        room.addUser(user);

        roomRepository.save(room);

        System.out.println("user " + user.getSessionId() + "가 방 " + room.getName() + "을 만들었습니다");
        s += "user " + user.getSessionId() + "가 방 " + room.getName() + "을 만들었습니다";
        List<Room> list = roomRepository.findAll();
        System.out.println("방 개수 = " + list.size());
        s += "방 개수 = " + list.size() + '\n';

        for(int i=0 ; i<list.size() ; i++) {
            Room r = list.get(i);
            System.out.println(i + "번 방제목 : " + r.getName());
            s += i + "번 방제목 : " + r.getName() + '\n';

            System.out.println("참가자");
            List<User> l = r.findAll();
            for(int j=0 ; j<l.size() ; j++)
                System.out.print(l.get(j).getSessionId());
            System.out.println();
            System.out.println();
        }
        return s;
    }

    @PostMapping("makeRoom")
    public String makeRoom(Model model) {
        User user = new User();
        user.setSessionId(httpSession.getId());
        user.setName(httpServletRequest.getParameter("_username_id"));

        Room room = new Room();
        room.setName("임시 방제목" + ++tempRoomCount);
        room.setRoomSize(2);
        room.addUser(user);

        roomRepository.save(room);

        model.addAttribute("roomname", room.getName());
//        httpServletRequest.setAttribute("roomname", room.getName());
        return "GamePlay.jsp";
    }
}
