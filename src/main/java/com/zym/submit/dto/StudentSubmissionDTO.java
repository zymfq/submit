package com.zym.submit.dto;

import lombok.Data;

/**
 * 提交的实验报告信息
 * @author zym
 * @date 2020-05-17-23:10
 */
@Data
public class StudentSubmissionDTO {

    private int number;//将查询到的学生信息进行编号

    private String studentNumber;

    private String studentName;

    private String reportPath;

    private String studentScore;
}
