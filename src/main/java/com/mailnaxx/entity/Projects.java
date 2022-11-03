package com.mailnaxx.entity;

import java.sql.Timestamp;

import lombok.Getter;

@Getter
public class Projects {

    private int project_id;
    private String company_name;
    private String project_name;
    private String deleted_flg;
    private int created_by;
    private Timestamp created_at;
    private int updated_by;
    private Timestamp updated_at;

}
