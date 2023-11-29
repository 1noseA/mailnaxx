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

    // 一括確認
    public void bulkConfirm(List<WeeklyReports> weeklyReportList);

    // 1件取得
    public WeeklyReports findById(int weeklyReportId);

    // 確認
    public void confirm(WeeklyReports weeklyReports);

    // 1件排他ロック
    public WeeklyReports forLockById(int weeklyReportId);

    // 複数件排他ロック
    public List<WeeklyReports> forLockByIdList(List<Integer> idList);

    // 登録
    public void insert(WeeklyReports weeklyReports);
}
