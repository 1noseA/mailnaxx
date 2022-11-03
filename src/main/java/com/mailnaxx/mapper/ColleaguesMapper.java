package com.mailnaxx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mailnaxx.entity.Colleagues;

@Mapper
public interface ColleaguesMapper {

    public List<Colleagues> findAll();

}
