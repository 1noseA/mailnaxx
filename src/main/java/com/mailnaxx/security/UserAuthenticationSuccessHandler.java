package com.mailnaxx.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.mailnaxx.mapper.UsersMapper;

public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UsersMapper usersMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // ログイン成功時更新
        String userNumber = authentication.getName();
        usersMapper.update(userNumber);
        response.sendRedirect(request.getContextPath() + "/top");
    }
}
