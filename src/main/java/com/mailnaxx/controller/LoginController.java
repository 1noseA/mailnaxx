package com.mailnaxx.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mailnaxx.entity.Users;
import com.mailnaxx.form.LoginForm;
import com.mailnaxx.mapper.UsersMapper;
import com.mailnaxx.security.LoginUserDetails;

@Controller
public class LoginController {

    @Autowired
    UsersMapper usersMapper;

    @ModelAttribute
    LoginForm loginForm() {
        return new LoginForm();
    }

    @GetMapping("/")
    public String showLoginPage(@ModelAttribute LoginForm loginForm) {
        return "login/login";
    }

    @PostMapping("/loginCheck")
    public String loginCheck(@Validated @ModelAttribute LoginForm loginForm, BindingResult result) {
        // 入力エラーがある場合、元の画面に戻る
        if (result.hasErrors()) {
            return "login/login";
        }
        // SecurityConfigで設定した認証処理にフォワードする
        return "redirect:/login";
    }

    // テストログイン
    @PostMapping("/testLogin")
    public String testLogin() {
        String userNumber = "20230805";
        Optional<Users> user = usersMapper.findLoginUser(userNumber);
        UserDetails userDetails = user.map(users -> new LoginUserDetails(users)).orElseThrow(() -> new UsernameNotFoundException("not found"));

        // 最終ログイン日時の更新とログイン失敗回数初期化
        usersMapper.update(userNumber);

        // セキュリティコンテキストの内容を更新
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/top";
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