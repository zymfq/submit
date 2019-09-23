package com.zhy.submit.teacher.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
public class addReportDTO {

    //学年
    private String schoolYear;
    //学期
    private String term;
    //年级
    private String grade;
    //班级
    private String schoolClass;
    //课程
    private String course;
    //教师发布实验id
    private String taskId;
    //学期学年id
    private Integer termId;
    //实验名称
    private String experimentName;
    //教师工号
    private String teacherNumber;
    //截止日期
    private Date submitDeadline;
    //存放路径
    private String taskPath;

    //班级id
    private  Integer  classId;
    //课程id
    private  Integer courseId;

    private MultipartFile file;
}
