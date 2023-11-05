package com.mailnaxx.form;

import java.util.List;

import lombok.Data;

/**
 * 社員選択
 */
@Data
public class SelectUsersForm {

    // 選択チェックボックス
    private List<Integer> selectUser;
}
