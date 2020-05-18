package com.zym.submit.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author zym
 * @date 2020-05-17-23:23
 */
@Data
public class DownloadReportDTO {
    //学年
    private  String studyYear;
    //学期
    private String termName;
    //年级
    private String gradeName;
    //班级
    private String className;
    //课程名称
    private String courseName;
    //实验名称
    private String TaskName;
    //实验Id
    private  Integer taskId;
    //班级平均分
    private Double averageScore;
    //创建日期
    private Date createTime;
    //截止日期
    private Date updateTime;
}
