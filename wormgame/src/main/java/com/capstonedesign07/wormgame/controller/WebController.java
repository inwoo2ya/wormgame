package com.capstonedesign07.wormgame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("")
    public String root() {
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
}
