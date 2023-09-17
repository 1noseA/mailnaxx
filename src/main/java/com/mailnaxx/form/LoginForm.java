package com.mailnaxx.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginForm {

    // 社員番号
    @NotBlank(message = "入力してください")
    private String userNumber;

    // パスワード
    @NotBlank(message = "入力してください")
    private String password;

}
