package com.mailnaxx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mailnaxx.entity.Notices;

@Mapper
public interface NoticesMapper {

    public List<Notices> selectAll();

}
