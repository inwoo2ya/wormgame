package com.capstonedesign07.wormgame.controller;

import com.capstonedesign07.wormgame.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Controller
public class WebController {

    @Autowired private HttpSession httpSession;
    @Autowired private HttpServletRequest httpServletRequest;

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
        return "Game.html";
    }
    
    @GetMapping("GamePlay")
    public String GamePlay() {
        return "GamePlay.html";
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
}
