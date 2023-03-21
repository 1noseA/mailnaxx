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
    @NotBlank(groups = ValidGroup1.class, message = "入力してください")
    @Size(max=10, groups = ValidGroup2.class, message = "全角10文字以内で入力してください")
    @Pattern(regexp="^[^ -~｡-ﾟ]+$", groups = ValidGroup3.class, message = "全角文字で入力してください")
    private String userLastName;

    // 氏名(漢字)_名
    @NotBlank(groups = ValidGroup1.class, message = "入力してください")
    @Size(max=10, groups = ValidGroup2.class, message = "全角10文字以内で入力してください")
    @Pattern(regexp="^[^ -~｡-ﾟ]+$", groups = ValidGroup2.class, message = "全角文字で入力してください")
    private String userFirstName;

    // 氏名(カタカナ)_姓
    @NotBlank(groups = ValidGroup1.class, message = "入力してください")
    @Size(max=10, groups = ValidGroup2.class, message = "全角10文字以内で入力してください")
    @Pattern(regexp="^[ァ-ー]+$", groups = ValidGroup2.class, message = "全角カナで入力してください")
    private String userLastKana;

    // 氏名(カタカナ)_名
    @NotBlank(groups = ValidGroup1.class, message = "入力してください")
    @Size(max=10, groups = ValidGroup2.class, message = "全角10文字以内で入力してください")
    @Pattern(regexp="^[ァ-ー]+$", groups = ValidGroup2.class, message = "全角カナで入力してください")
    private String userFirstKana;

    // 入社年月_年
    private int hireYear;

    // 入社年月_月
    private int hireMonth;

    // 所属
    @PositiveOrZero(groups = ValidGroup1.class, message = "選択してください")
    private int affiliationId;

    // 権限区分
    private String roleClass;

    // 営業担当
    private String salesFlg;

    // 生年月日_年
    @Positive(groups = ValidGroup1.class, message = "選択してください")
    private int birthYear;

    // 生年月日_月
    @Positive(groups = ValidGroup1.class, message = "選択してください")
    private int birthMonth;

    // 生年月日_日
    @Positive(groups = ValidGroup1.class, message = "選択してください")
    private int birthDay;

    // 郵便番号1
    @NotBlank(groups = ValidGroup1.class, message = "入力してください")
    @Pattern(regexp="^[0-9]+$", groups = ValidGroup2.class, message = "数字で入力してください")
    private String postCode1;

    // 郵便番号2
    @NotBlank(groups = ValidGroup1.class, message = "入力してください")
    @Pattern(regexp="^[0-9]+$", groups = ValidGroup2.class, message = "数字で入力してください")
    private String postCode2;

    // 住所
    @NotBlank(groups = ValidGroup1.class, message = "入力してください")
    @Size(max=256, groups = ValidGroup2.class, message = "全角256文字以内で入力してください")
    @Pattern(regexp="^[^ -~｡-ﾟ]+$", groups = ValidGroup2.class, message = "全角文字で入力してください")
    private String address;

    // 電話番号1
    @NotBlank(groups = ValidGroup1.class, message = "入力してください")
    @Pattern(regexp="^[0-9]+$", groups = ValidGroup2.class, message = "数字で入力してください")
    private String phoneNumber1;

    // 電話番号2
    @NotBlank(groups = ValidGroup1.class, message = "入力してください")
    @Pattern(regexp="^[0-9]+$", groups = ValidGroup2.class, message = "数字で入力してください")
    private String phoneNumber2;

    // 電話番号3
    @NotBlank(groups = ValidGroup1.class, message = "入力してください")
    @Pattern(regexp="^[0-9]+$", groups = ValidGroup2.class, message = "数字で入力してください")
    private String phoneNumber3;

    // メールアドレス
    @NotBlank(groups = ValidGroup1.class, message = "入力してください")
    @Size(max=128, groups = ValidGroup2.class, message = "半角128文字以内で入力してください")
    @Email(groups = ValidGroup3.class, message = "メールアドレスの形式が間違っています")
    private String emailAddress;

    // パスワード
    @NotBlank(groups = ValidGroup1.class, message = "入力してください")
    @Size(min=8, max=10, groups = ValidGroup2.class, message = "半角英数字8文字以上10文字以内で入力してください")
    @Pattern(regexp="^[A-Za-z0-9]+$", groups = ValidGroup3.class, message = "半角英数字で入力してください")
    private String password;

}
