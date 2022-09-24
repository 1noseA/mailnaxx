package com.mailnaxx.mapper;

import com.mailnaxx.entity.Users;

public interface LoginMapper {

    public Users findLoginUser(String userId);

}
