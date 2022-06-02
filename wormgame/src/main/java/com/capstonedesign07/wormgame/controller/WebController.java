package com.capstonedesign07.wormgame.controller;

import com.capstonedesign07.wormgame.domain.Room;
import com.capstonedesign07.wormgame.domain.RoomStatus;
import com.capstonedesign07.wormgame.domain.User;
import com.capstonedesign07.wormgame.repository.RoomRepository;
import com.capstonedesign07.wormgame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class WebController {

    private final static int FIXED_ROOM_COUNT = 8;
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
        model.addAttribute("userCount", userRepository.getSize()); // 22.05.20 접속자 수 로직 추기
        return "secondIndex.jsp";
    }
    
    @GetMapping("GamePlay")
    public String GamePlay() {
        return "GamePlay.jsp";
    }
    
    @GetMapping("joinRoom") // 2022.05.31 변경
    public String joinRoom(@RequestParam("roomNumber") int roomNumber, Model model) {
        if (!roomRepository.getRooms().get(roomNumber).canJoin())
            return findRoom(model);

        User user = userRepository.findBySessionId(httpSession.getId());
        roomRepository.getRooms().get(roomNumber).addUser(user);
        model.addAttribute("roomname", roomRepository.getRooms().get(roomNumber).getName());
        return "GamePlay.jsp";
    }

    @GetMapping("randomEntrance")
    public String randomEntrance(Model model){ //빠른입장 => 랜덤함수 사용하여 room리스트중 대기중 상태에 있는 방 중 하나 골라서 랜덤으로 입장
        List<Room> rooms = roomRepository.getRooms();
        List<Room> joinableRooms = IntStream.range(0, FIXED_ROOM_COUNT)
                .mapToObj(i -> rooms.get(i))
                .filter(Room::canJoin)
                .collect(Collectors.toList());

        if (joinableRooms.size() == 0)
            return secondIndex(model);

        Random random = new Random();
        int roomIndex = roomRepository.findRoomIndex(joinableRooms.get(random.nextInt(joinableRooms.size())));
        return joinRoom(roomIndex, model);
    }

    @GetMapping("findRoom") // 2022.05.27 변경
    public String findRoom(Model model) {
        List<Room> rooms = roomRepository.getRooms();
        IntStream.range(0, FIXED_ROOM_COUNT)
                .forEach(i -> {
                    model.addAttribute("room" + i + "Name", rooms.get(i).getName());
                    model.addAttribute("room" + i + "UsersSize", rooms.get(i).roomUsers().size());
                });
        return "findRoom.jsp";
    }

    @GetMapping("admin")
    @ResponseBody
    public String admin() {
        StringBuilder sb = new StringBuilder("전체 유저 : ");
        List<User> users = userRepository.findAll().getUsers();
        sb.append(users.size() + "명" + "<br/>");
        IntStream.range(0, users.size())
                .mapToObj(i -> users.get(i).getSessionId() + " : " + users.get(i).getName() + "<br/>")
                .forEach(sb::append);

        List<Room> rooms = roomRepository.getRooms();
        sb.append("<br/>방 목록<br/>");
        IntStream.range(0, FIXED_ROOM_COUNT)
                .mapToObj(i -> {
                    StringBuilder roomInfo = new StringBuilder(i + "번방");
                    RoomStatus status = rooms.get(i).getRoomStatus();
                    if (status.equals(RoomStatus.WAIT)) {
                        roomInfo.append("(WAIT) : ");
                    }
                    if (status.equals(RoomStatus.PLAYING)) {
                        roomInfo.append("(PLAYING) : ");
                    }
                    roomInfo.append(rooms.get(i).getName() + "<br/>");

                    List<User> roomUsers = rooms.get(i).roomUsers();
                    IntStream.range(0, roomUsers.size())
                            .mapToObj(j -> roomUsers.get(j).getSessionId() + " : " + roomUsers.get(j).getName() + "<br/>")
                            .forEach(roomInfo::append);

                    roomInfo.append("<br/>");
                    return roomInfo;
                })
                .forEach(sb::append);
        return sb.toString();
    }
}
