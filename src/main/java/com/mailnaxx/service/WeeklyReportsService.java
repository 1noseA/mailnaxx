package com.mailnaxx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mailnaxx.entity.WeeklyReports;
import com.mailnaxx.mapper.WeeklyReportsMapper;

@Service
public class WeeklyReportsService {

    @Autowired
    WeeklyReportsMapper weeklyReportsMapper;

    // 全件取得
    public List<WeeklyReports> findAll() {
        List<WeeklyReports> weeklyReportList = weeklyReportsMapper.findAll();
        return weeklyReportList;
    }

    // 詳細情報取得
    public WeeklyReports findById(int weeklyReportId) {
        WeeklyReports weeklyReportInfo = weeklyReportsMapper.findById(weeklyReportId);
        return weeklyReportInfo;
    }
}
