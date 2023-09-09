package com.mailnaxx.security;

import java.util.Optional;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mailnaxx.entity.Users;
import com.mailnaxx.mapper.UsersMapper;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    private final UsersMapper mapper;

    public LoginUserDetailsService(UsersMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String number) throws UsernameNotFoundException {
        Optional<Users> userOp = mapper.findLoginUser(number);
        return userOp.map(users -> new LoginUserDetails(users))
                .orElseThrow(() -> new UsernameNotFoundException("not found"));
    }

//    // ログイン失敗時のハンドラ
//    @EventListener
//    public void loginFailureHandle(AuthenticationFailureBadCredentialsEvent event) {
//        String userNumber = event.getAuthentication().getName();
//        mapper.incrementFailureCount(userNumber);
//    }

    // ログイン成功時のハンドラ
    @EventListener
    public void loginSuccessHandle(AuthenticationSuccessEvent event) {
        String userNumber = event.getAuthentication().getName();
        // 最終ログイン日時の更新とログイン失敗回数初期化
        mapper.update(userNumber);
    }
}
