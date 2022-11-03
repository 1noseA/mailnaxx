package com.mailnaxx.entity;

import java.sql.Timestamp;

import lombok.Getter;

@Getter
public class Colleagues {

    private int colleague_id;
    private int weekly_report_id;
    private int user_id;
    private int difficulty;
    private int schedule;
    private String impression;
    private int created_by;
    private Timestamp created_at;
    private int updated_by;
    private Timestamp updated_at;

}
