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

}
