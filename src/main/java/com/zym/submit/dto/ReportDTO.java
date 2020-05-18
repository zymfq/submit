package com.zym.submit.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 提供缓存的序列化支持
 * @author zym
 * @date 2019-09-06-20:09
 */
@Data
public class ReportDTO implements Serializable {

    private Integer taskId;

    private String reportName;

    private String courseName;

    private String teacherName;

    private Byte reportCorrect;

    private Double taskGrade;

    private Date createTime;
}
