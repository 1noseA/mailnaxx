package com.mailnaxx.values;

// ユーザ.権限区分
public enum RoleClass {

    Member("1", "一般"),
    LeaderOrChief("2", "リーダ・チーフ"),
    Manager("3", "マネジャー"),
    GeneralAffairs("4", "総務"),
    President("5", "社長");

    private final String value;
    private final String viewName;

    private RoleClass(String value, String viewName) {
        this.value = value;
        this.viewName = viewName;
    }

    public String getValue() {
        return this.value;
    }

    public String getViewName() {
        return this.viewName;
    }

    // コード値からの逆引き
    public static RoleClass getByCode(String code) {
        // 値からenum定数を特定して返す
        for (RoleClass value : RoleClass.values()) {
            if (value.getValue() == code) {
                return value;
            }
        }
        // 特定できない場合
        return null;
    }

}
