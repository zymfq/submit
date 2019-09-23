package com.zhy.submit.teacher.dto;

import lombok.Data;

@Data
public class StudentSubmissionDTO {
    private int number;//将查询到的学生信息进行编号
    private String studentNumber;
    private String studentName;
    private String reportPath;
    private String studentScore;
}
