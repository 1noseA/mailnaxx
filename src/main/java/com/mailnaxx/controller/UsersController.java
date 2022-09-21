package com.mailnaxx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mailnaxx.entity.Affiliations;
import com.mailnaxx.entity.Users;
import com.mailnaxx.mapper.AffiliationsMapper;
import com.mailnaxx.mapper.UsersMapper;

@Controller
public class UsersController {

    @Autowired
    UsersMapper usersMapper;
    AffiliationsMapper affiliationsMapper;

    @RequestMapping("/users")
    public String index(Model model) {

        List<Users> usersList = usersMapper.selectAll();
        model.addAttribute("usersList", usersList);
        return "users/index";

    }

    // 登録画面初期表示
    @RequestMapping(value="/register")
    public String register(Model model) {

        List<Affiliations> affiliationList = affiliationsMapper.selectAll();
        model.addAttribute("affiliationList", affiliationList);
        return "users/register";

    }

    // 登録画面登録処理
    @RequestMapping(value="/register")
    public String register(Users users) {

        usersMapper.register(users);
        return "users/register";

    }
}
