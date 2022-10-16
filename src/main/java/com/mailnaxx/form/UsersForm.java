package com.mailnaxx.form;

import lombok.Data;

@Data
public class UsersForm {

    // 氏名(漢字)_姓
    private String userLastName;

    // 氏名(漢字)_名
    private String userFirstName;

    // 氏名(カタカナ)_姓
    private String userLastKana;

    // 氏名(カタカナ)_名
    private String userFirstKana;

    // 入社年月_年
    private int hireYear;

    // 入社年月_月
    private int hireMonth;

    // 所属
    private int affiliationId;

    // 権限区分
    private String roleClass;

    // 営業担当
    private String salesFlg;

    // 生年月日_年
    private int birthYear;

    // 生年月日_月
    private int birthMonth;

    // 生年月日_日
    private int birthDay;

    // 郵便番号1
    private String postCode1;

    // 郵便番号2
    private String postCode2;

    // 住所
    private String address;

    // 電話番号1
    private String phoneNumber1;

    // 電話番号2
    private String phoneNumber2;

    // 電話番号3
    private String phoneNumber3;

    // メールアドレス
    private String emailAddress;

    // パスワード
    private String password;

}
