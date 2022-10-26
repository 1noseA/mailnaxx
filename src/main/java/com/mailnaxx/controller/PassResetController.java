package com.mailnaxx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PassResetController {

    @RequestMapping("/pass-reset")
    public String showPassResetPage(Model model) {

        return "pass-reset";

    }
}