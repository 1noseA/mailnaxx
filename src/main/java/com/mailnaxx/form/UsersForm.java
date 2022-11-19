package com.mailnaxx.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UsersForm {

    // 氏名(漢字)_姓
    @NotBlank(message = "氏名(漢字)_姓を入力してください")
    @Size(max=10, message = "全角10文字以内で入力してください")
    @Pattern(regexp="^[^ -~｡-ﾟ]+$", message = "全角文字で入力してください")
    private String userLastName;

    // 氏名(漢字)_名
    @NotBlank(message = "氏名(漢字)_名を入力してください")
    @Size(max=10, message = "全角10文字以内で入力してください")
    @Pattern(regexp="^[^ -~｡-ﾟ]+$", message = "全角文字で入力してください")
    private String userFirstName;

    // 氏名(カタカナ)_姓
    @NotBlank(message = "氏名(カタカナ)_姓を入力してください")
    @Size(max=10, message = "全角10文字以内で入力してください")
    @Pattern(regexp="^[ァ-ー]+$", message = "全角カナで入力してください")
    private String userLastKana;

    // 氏名(カタカナ)_名
    @NotBlank(message = "氏名(カタカナ)_名を入力してください")
    @Size(max=10, message = "全角10文字以内で入力してください")
    @Pattern(regexp="^[ァ-ー]+$", message = "全角カナで入力してください")
    private String userFirstKana;

    // 入社年月_年
    @Positive(message = "入社年月_年を入力してください")
    private int hireYear;

    // 入社年月_月
    @Positive(message = "入社年月_年を入力してください")
    private int hireMonth;

    // 所属
    @PositiveOrZero(message = "所属を入力してください")
    private int affiliationId;

    // 権限区分
    @NotBlank(message = "権限区分を入力してください")
    private String roleClass;

    // 営業担当
    private String salesFlg;

    // 生年月日_年
    @Positive(message = "生年月日_年を入力してください")
    private int birthYear;

    // 生年月日_月
    @Positive(message = "生年月日_月を入力してください")
    private int birthMonth;

    // 生年月日_日
    @Positive(message = "生年月日_日を入力してください")
    private int birthDay;

    // 郵便番号1
    @NotBlank(message = "郵便番号1を入力してください")
    private String postCode1;

    // 郵便番号2
    @NotBlank(message = "郵便番号2を入力してください")
    private String postCode2;

    // 住所
    @NotBlank(message = "住所を入力してください")
    @Size(max=256, message = "全角256文字以内で入力してください")
    @Pattern(regexp="^[^ -~｡-ﾟ]+$", message = "全角文字で入力してください")
    private String address;

    // 電話番号1
    @NotBlank(message = "電話番号1を入力してください")
    @Pattern(regexp="^[0-9]+$", message = "電話番号1は数字5桁以内で入力してください")
    private String phoneNumber1;

    // 電話番号2
    @NotBlank(message = "電話番号2を入力してください")
    @Pattern(regexp="^[0-9]+$", message = "電話番号2は数字4桁以内で入力してください")
    private String phoneNumber2;

    // 電話番号3
    @NotBlank(message = "電話番号3を入力してください")
    @Pattern(regexp="^[0-9]+$", message = "電話番号3は数字4桁以内で入力してください")
    private String phoneNumber3;

    // メールアドレス
    @NotBlank(message = "メールアドレスを入力してください")
    @Size(max=128, message = "半角128文字以内で入力してください")
    @Email(message = "メールアドレスの形式が間違っています")
    private String emailAddress;

    // パスワード
    @NotBlank(message = "パスワードを入力してください")
    @Size(min=8, max=10, message = "半角英数字8文字以上10文字以内で入力してください")
    @Pattern(regexp="^[A-Za-z0-9]+$", message = "半角英数字で入力してください")
    private String password;

}
