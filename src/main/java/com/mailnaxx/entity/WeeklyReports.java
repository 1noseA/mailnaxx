package com.mailnaxx.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class WeeklyReports {

    private int weekly_report_id;
    private int user_id;
    private String sales_name;
    private int project_id;
    private String report_date;
    private int ave_overtime_hours;
    private String progress;
    private String condition;
    private String relationship;
    private String plan;
    private String work_content;
    private int difficulty;
    private int schedule;
    private String result;
    private String realization;
    private String improvements;
    private String next_plan;
    private String remarks;
    private String confirmed_flg;
    private String comment;
    private String shared_flg;
    private int created_by;
    private Timestamp created_at;
    private int updated_by;
    private Timestamp updated_at;

}
