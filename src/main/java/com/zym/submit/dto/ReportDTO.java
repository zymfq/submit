package com.zym.submit.dto;

import lombok.Data;
import java.util.Date;

/**
 * @author zym
 * @date 2019-09-06-20:09
 */
@Data
public class ReportDTO {

    private Integer taskId;

    private String reportName;

    private String courseName;

    private String teacherName;

    private Byte reportCorrect;

    private Double taskGrade;

    private Date createTime;
}
