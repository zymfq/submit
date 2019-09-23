package com.zym.submit.dto;

        import lombok.Data;

        import java.util.Date;

/**
 * @author zym
 * @date 2019-09-16-10:52
 */
@Data
public class TaskDTO {

    private Integer taskId;

    private String courseName;

    private String taskName;

    private String teacherName;

    private String studyYear;

    private String termName;

    private Date submitDeadline;

}
