package com.mailnaxx.entity;

import java.sql.Timestamp;
import java.time.LocalDate;

import lombok.Getter;

@Getter
public class Notices {

    private int notice_id;
    private LocalDate start_date;
    private LocalDate end_date;
    private String display_range;
    private int user_id;
    private String notice_message;
    private String reference_link;
    private int created_by;
    private Timestamp created_at;
    private int updated_by;
    private Timestamp updated_at;

}
