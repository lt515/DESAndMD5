package com.zsc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("")
    public String login(){
        return "index.html";
    }

    @GetMapping("file")
    public String file(){
        return "file.html";
    }

}
