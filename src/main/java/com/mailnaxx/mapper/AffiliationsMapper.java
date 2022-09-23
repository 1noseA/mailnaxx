package com.mailnaxx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mailnaxx.entity.Affiliations;

@Mapper
public interface AffiliationsMapper {

    public List<Affiliations> selectAll();

}
