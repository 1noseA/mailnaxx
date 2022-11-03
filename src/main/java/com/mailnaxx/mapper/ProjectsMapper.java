package com.mailnaxx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mailnaxx.entity.Projects;

@Mapper
public interface ProjectsMapper {

    public List<Projects> findAll();

}
