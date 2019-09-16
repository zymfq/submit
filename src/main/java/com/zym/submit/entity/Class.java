package com.zym.submit.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Class {

    private Integer classId;

    private String className;

    private Integer gradeId;

    private Integer collegeId;

    private Integer teacherId;

    private Byte isValid;

    private Date createTime;

    private Date updateTime;

}