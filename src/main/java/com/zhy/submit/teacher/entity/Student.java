package com.zhy.submit.teacher.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Student {
    private Integer studentId;

    private String studentNumber;

    private String studentName;

    private String studentPassword;

    private Byte studentSex;

    private Integer classId;

    private Byte isValid;

    private Date createTime;

    private Date updateTime;


}