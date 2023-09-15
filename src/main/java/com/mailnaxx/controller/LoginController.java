package com.mailnaxx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mailnaxx.form.LoginForm;

@Controller
public class LoginController {

    @ModelAttribute
    LoginForm loginForm() {
        return new LoginForm();
    }

    @GetMapping("/")
    public String showLoginPage() {
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm form, BindingResult result) {
        // 入力エラーがある場合、元の画面に戻る
        if (result.hasErrors()) {
            return "login/login";
        }
        // SecurityConfigで設定した認証処理にフォワードする
        return "forward:/login";
    }

    @GetMapping("/reset")
    public String showResetPage(Model model) {
        return "login/reset";
    }

    @GetMapping("/logout")
    public String logout() {
        return "/";
    }
}