package com.zhy.submit.teacher.dto;

import lombok.Data;

@Data
public class AverageScoreOfCourseDTO {
    private String studyYear;
    private String termName;
    private int termId;
    private String gradeName;
    private String className;
    private int classId;
    private String courseName;
    private int courseId;

}
