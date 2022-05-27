package com.capstonedesign07.wormgame.controller;

import com.capstonedesign07.wormgame.domain.Room;
import com.capstonedesign07.wormgame.domain.User;
import com.capstonedesign07.wormgame.repository.RoomRepository;
import com.capstonedesign07.wormgame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.IntStream;

@Controller
public class WebController {

    private final static int FIXED_ROOM_COUNT = 8;
    int user_count = 0 ; // 22.05.20 접속자 수 변경
    int tempRoomCount = 0;
    @Autowired private HttpSession httpSession;
    @Autowired private HttpServletRequest httpServletRequest;
    private UserRepository userRepository;
    private RoomRepository roomRepository;

    @Autowired
    public WebController(UserRepository userRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @GetMapping("")
    public String root() {
        return firstIndex();
    }

    @GetMapping("index")
    public String index() {
        return firstIndex();
    }

    @GetMapping("firstIndex")
    public String firstIndex() {
        System.out.println("httpSession.getId() = " + httpSession.getId());
        return "firstIndex.jsp";
    }

    @PostMapping("firstPost")
    public String firstPost(Model model) {
        User user = new User(httpSession.getId(), httpServletRequest.getParameter("_username_id"));
        userRepository.save(user);

        httpSession.setAttribute("userName", user.getName());
        return secondIndex(model);
    }

    @GetMapping("secondIndex") // 2022.05.22 변경
    public String secondIndex(Model model) {
        model.addAttribute("user_name", ++user_count); // 22.05.20 접속자 수 로직 추기
        return "secondIndex.jsp";
    }
    
    @GetMapping("GamePlay")
    public String GamePlay() {
        return "GamePlay.jsp";
    }
    
    @PostMapping("GameRoom") // 2022.05.26 변경
    public String makeRoom(Model model) {
        User user = new User(httpSession.getId(), httpServletRequest.getParameter("_username_id"));

        Room room = new Room(httpServletRequest.getParameter("roomname"));
        room.addUser(user);

//        roomRepository.save(room);
        System.out.println("roomname: "+httpServletRequest.getParameter("roomname"));
        model.addAttribute("roomname", room.getName());
//        httpServletRequest.setAttribute("roomname", room.getName());
        return "GamePlay.jsp";
    }
    @GetMapping("findRoom") // 2022.05.27 변경
    public String findRoom(Model model) {
        List<Room> rooms = roomRepository.getRooms();
        IntStream.range(0, FIXED_ROOM_COUNT)
                .forEach(i -> model.addAttribute("room" + i + "Name", rooms.get(i).getName()));
        return "findRoom.jsp";
    }
}
