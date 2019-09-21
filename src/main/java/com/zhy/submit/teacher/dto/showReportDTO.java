package com.zhy.submit.teacher.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class showReportDTO {
    //学年
    private  String schoolYear;
    //学期
    private String termName;
    //年级
    private String grade;
    //班级
    private String schoolClass;
    //课程名称
    private String course;
    //实验名称
    private String experimentName;
    //实验Id
    private  String taskId;
    //班级平均分
    private Double averageScore;
    //创建日期
    private Date createTime;
    //截止日期
    private Date submitDeadline;

}
