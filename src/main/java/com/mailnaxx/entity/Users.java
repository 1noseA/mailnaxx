package com.mailnaxx.entity;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * ユーザ
 */
@Data
public class Users {

    // ユーザID
    private int user_id;

    // 社員番号
    private String user_number;

    // 氏名_漢字
    private String user_name;

    // 氏名_カナ
    private String user_name_kana;

    // 入社年月
    private String hire_date;

    // 所属ID
    private int affiliation_id;

    // 権限区分
    private String role_class;

    // 営業フラグ
    private String sales_flg;

    // 生年月日
    private String birth_date;

    // 郵便番号
    private String post_code;

    // 住所
    private String address;

    // 電話番号
    private String phone_number;

    // メールアドレス
    private String email_address;

    // パスワード
    private String password;

    // パスワード変更日時
    private LocalDateTime pass_changed_date;

    // 前回パスワード
    private String old_password;

    // ログイン失敗回数
    private int failure_count;

    // 最終ログイン日時
    private LocalDateTime last_login_date;

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
