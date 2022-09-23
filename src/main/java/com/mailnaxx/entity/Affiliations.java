package com.mailnaxx.entity;

import java.sql.Timestamp;

import lombok.Getter;

@Getter
public class Affiliations {

    private int affiliation_id;
    private String affiliation_name;
    private String administrator_name;
    private boolean is_hidden;
    private String created_by;
    private Timestamp created_at;
    private String updated_by;
    private Timestamp updated_at;

}
