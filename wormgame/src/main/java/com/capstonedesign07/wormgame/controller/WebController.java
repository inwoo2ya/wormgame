package com.capstonedesign07.wormgame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/jsp")
    public String jspPage() {
        return "index";
    }
}
