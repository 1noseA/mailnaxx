package com.mailnaxx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mailnaxx.entity.Users;
import com.mailnaxx.mapper.UsersMapper;

@Controller
public class UsersController {

    @Autowired
    UsersMapper usersMapper;

    @RequestMapping("/users")
    public String index(Model model) {

        List<Users> usersList = usersMapper.selectAll();
        model.addAttribute("usersList", usersList);
        return "users/index";

    }
}
