package com.zym.submit.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Report {

    private Integer id;

    private Integer taskId;

    private Integer classId;

    private String studentNumber;

    private String reportName;

    private String reportPath;

    private Byte reportCorrect;

    private Double taskGrade;

    private Byte isValid;

    private Date createTime;

    private Date updateTime;

}