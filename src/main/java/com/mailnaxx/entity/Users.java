package com.mailnaxx.entity;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Getter;

@Getter
public class Users {

    private String userId;
    private String userName;
    private Date hireDate;
    private int affiliationId;
    private String roleClass;
    private boolean isSales;
    private Date birthDate;
    private String address;
    private String phoneNumber;
    private String emailAddress;
    private byte[] password;
    private Timestamp passChangedDate;
    private byte[] oldPassword;
    private Timestamp lastLoginDate;
    private boolean isDeleted;
    private String createdBy;
    private Timestamp createdAt;
    private String updatedBy;
    private Timestamp updatedAt;

}
