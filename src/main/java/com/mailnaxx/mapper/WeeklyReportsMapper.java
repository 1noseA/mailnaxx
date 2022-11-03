package com.mailnaxx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mailnaxx.entity.WeeklyReports;

@Mapper
public interface WeeklyReportsMapper {

    public List<WeeklyReports> findAll();

    public WeeklyReports findOne(int weekly_report_id);

    public void insert(WeeklyReports weeklyReports);

    public void delete(WeeklyReports weeklyReports);

}
