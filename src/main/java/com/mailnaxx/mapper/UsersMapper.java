package com.mailnaxx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mailnaxx.entity.Users;

@Mapper
public interface UsersMapper {

    public List<Users> selectAll();

}
