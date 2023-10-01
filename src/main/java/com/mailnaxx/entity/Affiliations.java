package com.mailnaxx.entity;

import java.time.LocalDateTime;

import lombok.Getter;

/**
 * 所属
 */
@Getter
public class Affiliations {

    // 所属ID
    private int affiliation_id;

    // 所属名
    private String affiliation_name;

    // 管理者
    private int administrator_user_id;

    // 非表示フラグ
    private String hidden_flg;

    // レコード登録者
    private String created_by;

    // レコード登録日
    private LocalDateTime created_at;

    // レコード更新者
    private String updated_by;

    // レコード更新日
    private LocalDateTime updated_at;
}
