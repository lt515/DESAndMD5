package com.zsc.web.controller;

import com.zsc.web.util.DESUtil;
import com.zsc.web.domain.User;
import com.zsc.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final String key = "12345678";

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public String login(User user) throws Exception {
        User u = userService.findByUsername(user.getUsername());
        if(userService.login(user,u,key)){
            return "success";
        }
        return "error";
    }

    @PostMapping("/regist")
    @ResponseBody
    public User regist(User user) throws Exception {
        user.setPassword(DESUtil.encryption(user.getPassword(), key));
        return userService.regist(user);
    }

}
