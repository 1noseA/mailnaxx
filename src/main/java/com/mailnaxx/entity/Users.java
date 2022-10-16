package com.mailnaxx.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Users {

    private int user_id;
    private String user_number;
    private String user_name;
    private String user_name_kana;
    private String hire_date;
    private int affiliation_id;
    private String role_class;
    private String sales_flg;
    private String birth_date;
    private String post_code;
    private String address;
    private String phone_number;
    private String email_address;
    private String password;
    private Timestamp pass_changed_date;
    private String old_password;
    private Timestamp last_login_date;
    private String deleted_flg;
    private int created_by;
    private Timestamp created_at;
    private int updated_by;
    private Timestamp updated_at;

}
