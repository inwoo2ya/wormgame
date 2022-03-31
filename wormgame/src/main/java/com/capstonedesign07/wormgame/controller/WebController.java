package com.capstonedesign07.wormgame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    @RequestMapping("/")
    public String root() {
        return "index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
    
    @RequestMapping("/Game")
    public String Game() {
        return "Game";
    }
}
