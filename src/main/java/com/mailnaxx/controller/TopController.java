package com.mailnaxx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mailnaxx.entity.Notices;
import com.mailnaxx.mapper.NoticesMapper;

@Controller
public class TopController {

    @Autowired
    NoticesMapper noticesMapper;

    @RequestMapping("/top")
    public String index(Model model) {

        List<Notices> noticeList = noticesMapper.findAll();
        model.addAttribute("noticeList", noticeList);
        return "top/top";

    }

}
