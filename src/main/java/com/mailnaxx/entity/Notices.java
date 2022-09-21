package com.mailnaxx.entity;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Getter;

@Getter
public class Notices {

    private int noticeId;
    private Date startDate;
    private Date endDate;
    private String displayRange;
    private String userId;
    private String noticeMessage;
    private String referenceLink;
    private String createdBy;
    private Timestamp createdAt;
    private String updatedBy;
    private Timestamp updatedAt;

}
