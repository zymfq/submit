package com.zym.submit.entity;

import lombok.Data;
import java.util.Date;

@Data
public class TaskNotice {

    private Integer taskId;

    private Integer termId;

    private Integer classId;

    private String taskName;

    private Integer courseId;

    private String teacherNumber;

    private String taskPath;

    private Byte isValid;

    private Date submitDeadline;

    private Date createTime;

    private Date updateTime;

}