package com.zym.submit.dto;

import lombok.Data;

/**
 * @author zym
 * @date 2019-09-06-20:09
 */
@Data
public class ReportDTO {

    private Integer courseId;

    private Integer taskId;

    private Integer termId;

    private String studyYear;

    private Byte termName;

    private String reportName;

    private Byte reportCorrect;

    private Double taskGrade;

    private String reportPath;

    private StudentDTO studentDTO;

}
