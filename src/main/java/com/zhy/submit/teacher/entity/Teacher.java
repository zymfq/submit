package com.zhy.submit.teacher.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Teacher {
    private Integer teacherId;

    private String teacherNumber;

    private String teacherName;

    private String teacherPassword;

    private Byte teacherSex;

    private Byte identity;

    private Byte isValid;

    private Date createTime;

    private Date updateTime;


}