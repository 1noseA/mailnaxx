package com.mailnaxx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String showLoginPage(Model model) {

        return "login/login";

    }

    @RequestMapping("/reset")
    public String showPassResetPage(Model model) {

        return "login/reset";

    }
}