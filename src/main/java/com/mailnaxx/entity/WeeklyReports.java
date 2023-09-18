package com.mailnaxx.entity;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 週報
 */
@Data
public class WeeklyReports {

    // 週報ID
    private int weekly_report_id;

    // ユーザID
    private int user_id;

    // 担当営業
    private int sales_user_id;

    // 現場ID
    private int project_id;

    // 報告対象週
    private String report_date;

    // 平均残業時間
    private int ave_overtime_hours;

    // 進捗状況
    private String progress;

    // 体調
    private String condition;

    // 人間関係
    private String relationship;

    // 今週の計画
    private String plan;

    // 作業内容
    private String work_content;

    // 難易度
    private int difficulty;

    // スケジュール感
    private int schedule;

    // 結果
    private String result;

    // 所感
    private String impression;

    // 改善点
    private String improvements;

    // 次週の計画
    private String next_plan;

    // 特記事項
    private String remarks;

    // 確認済フラグ
    private String confirmed_flg;

    // コメント
    private String comment;

    // 共有フラグ
    private String shared_flg;

    // レコード登録者
    private String created_by;

    // レコード登録日
    private LocalDateTime created_at;

    // レコード更新者
    private String updated_by;

    // レコード更新日
    private LocalDateTime updated_at;
}
