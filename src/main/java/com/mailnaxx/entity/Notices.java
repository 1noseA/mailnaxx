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
    private int user_id;
    private String notice_message;
    private String reference_link;
    private int created_by;
    private Timestamp created_at;
    private int updated_by;
    private Timestamp updated_at;

}
