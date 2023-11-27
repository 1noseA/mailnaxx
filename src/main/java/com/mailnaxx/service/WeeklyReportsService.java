package com.mailnaxx.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mailnaxx.entity.WeeklyReports;
import com.mailnaxx.form.SearchWeeklyReportForm;
import com.mailnaxx.form.SelectForm;
import com.mailnaxx.mapper.WeeklyReportsMapper;
import com.mailnaxx.security.LoginUserDetails;

@Service
public class WeeklyReportsService {

    @Autowired
    WeeklyReportsMapper weeklyReportsMapper;

    // 全件取得
    public List<WeeklyReports> findAll() {
        List<WeeklyReports> weeklyReportList = weeklyReportsMapper.findAll();
        return weeklyReportList;
    }

    // 検索結果取得
    public List<WeeklyReports> findBySearchForm(SearchWeeklyReportForm searchWeeklyReportForm) {
        List<WeeklyReports> weeklyReportList = weeklyReportsMapper.findBySearchForm(searchWeeklyReportForm);
        return weeklyReportList;
    }

    // 一括確認処理
    @Transactional
    public void confirm(SelectForm selectForm, @AuthenticationPrincipal LoginUserDetails loginUser) {
        List<Integer> idList = new ArrayList<>();
        for (int i = 0; i < selectForm.getSelectTarget().size(); i++) {
            idList.add(selectForm.getSelectTarget().get(i));
        }
        // 複数件排他ロック
        List<WeeklyReports> weeklyReportList = weeklyReportsMapper.forLockByIdList(idList);

        for (int i = 0; i < weeklyReportList.size(); i++) {
            // 更新者はセッションの社員番号
            weeklyReportList.get(i).setUpdatedBy(loginUser.getLoginUser().getUserNumber());
        }

        // 論理削除
        weeklyReportsMapper.confirm(weeklyReportList);
    }

    // 詳細情報取得
    public WeeklyReports findById(int weeklyReportId) {
        WeeklyReports weeklyReportInfo = weeklyReportsMapper.findById(weeklyReportId);
        return weeklyReportInfo;
    }
}
