package com.mailnaxx.form;

import java.util.List;

import javax.validation.constraints.Size;

import com.mailnaxx.validation.ValidGroup1;

import lombok.Data;

/**
 * 社員選択
 */
@Data
public class SelectUsersForm {

    // 選択チェックボックス
    @Size(min = 1, groups = ValidGroup1.class, message = "選択してください")
    private List<Integer> userId;
}
