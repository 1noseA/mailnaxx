package com.mailnaxx.entity;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Getter;

@Getter
public class Notices {

    private int notice_id;
    private Date start_date;
    private Date end_date;
    private String display_range;
    private String user_id;
    private String notice_message;
    private String reference_link;
    private String created_by;
    private Timestamp created_at;
    private String updated_by;
    private Timestamp updated_at;

}
