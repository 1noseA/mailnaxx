package com.mailnaxx.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mailnaxx.entity.WeeklyReports;
import com.mailnaxx.form.SearchWeeklyReportForm;

@Mapper
public interface WeeklyReportsMapper {

    // 全件取得
    public List<WeeklyReports> findAll();

    // 検索
    public List<WeeklyReports> findBySearchForm(SearchWeeklyReportForm searchWeeklyReportForm);

    // 1件取得
    public WeeklyReports findById(int weeklyReportId);

    // 登録
    public void insert(WeeklyReports weeklyReports);

    // 削除
    public void delete(WeeklyReports weeklyReports);
}
