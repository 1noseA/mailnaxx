package com.mailnaxx.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginForm {

    // 社員番号
    @NotBlank(groups = ValidGroup1.class, message = "入力してください")
    private String userNumber;

    // パスワード
    @NotBlank(groups = ValidGroup1.class, message = "入力してください")
    private String password;

}
