package com.mailnaxx.form;

import lombok.Data;

@Data
public class WeeklyReportForm {

    // 担当営業
    private String sales_name;

    // 現場ID
    private int projectId;

    // 報告対象週
    private String reportDate;

    // 平均残業時間
    private int aveOvertimeHours;

    // 進捗状況
    private String progress;

    // 体調
    private String condition;

    // 人間関係
    private String relationship;

    // 今週の計画
    private String plan;

    // 作業内容
    private String workContent;

    // 難易度
    private int difficulty;

    // スケジュール感
    private int schedule;

    // 成果
    private String result;

    // 気づき
    private String realization;

    // 改善点
    private String improvements;

    // 次週の計画
    private String nextPlan;

    // 特記事項
    private String remarks;

    // 現場社員有無
    private String colleagueExists;

}
