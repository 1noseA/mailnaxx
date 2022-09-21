package com.mailnaxx.entity;

import java.sql.Timestamp;

import lombok.Getter;

@Getter
public class Affiliations {

    private int affiliationId;
    private String affiliationName;
    private String administratorName;
    private boolean isHidden;
    private String createdBy;
    private Timestamp createdAt;
    private String updatedBy;
    private Timestamp updatedAt;

}
