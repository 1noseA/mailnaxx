package com.mailnaxx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mailnaxx.entity.Notices;
import com.mailnaxx.mapper.NoticesMapper;
import com.mailnaxx.security.LoginUserDetails;

@Controller
public class TopController {

    @Autowired
    NoticesMapper noticesMapper;

    @RequestMapping("/top")
    public String index(Model model, @AuthenticationPrincipal LoginUserDetails loginUser) {
        List<Notices> noticeList = noticesMapper.findAll();
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("loginUserInfo", loginUser.getLoginUser());
        return "top/top";
    }
}
