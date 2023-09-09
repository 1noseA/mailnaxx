package com.mailnaxx.security;

import java.util.Optional;

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
}
