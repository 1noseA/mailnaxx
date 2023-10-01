package com.mailnaxx.entity;

import java.time.LocalDateTime;

import lombok.Getter;

/**
 * 現場
 */
@Getter
public class Projects {

    // 現場ID
    private int project_id;

    // 会社名
    private String company_name;

    // 案件名
    private String project_name;

    // 担当営業
    private int sales_user_id;

    // 削除フラグ
    private String deleted_flg;

    // レコード登録者
    private String created_by;

    // レコード登録日
    private LocalDateTime created_at;

    // レコード更新者
    private String updated_by;

    // レコード更新日
    private LocalDateTime updated_at;
}
