package com.mailnaxx.entity;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 所属
 */
@Data
public class Affiliations {

    // 所属ID
    private int affiliationId;

    // 所属名
    private String affiliationName;

    // 管理者
    private int administratorUserId;

    // 非表示フラグ
    private String hiddenFlg;

    // レコード登録者
    private String createdBy;

    // レコード登録日
    private LocalDateTime createdAt;

    // レコード更新者
    private String updatedBy;

    // レコード更新日
    private LocalDateTime updatedAt;
}
