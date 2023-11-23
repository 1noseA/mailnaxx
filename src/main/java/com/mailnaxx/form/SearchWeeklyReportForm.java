package com.mailnaxx.form;

import lombok.Data;

/**
 * 週報検索
 */
@Data
public class SearchWeeklyReportForm {

    // チーム
    private String affiliationId;

    // 担当営業
    private String salesUserId;

    // 氏名
    private String userName;

    // 報告対象週
    private String reportDate;

    // 確認済みフラグ
    private String confirmedFlg;
}
