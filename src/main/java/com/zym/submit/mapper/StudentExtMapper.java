package com.zym.submit.mapper;

import com.zym.submit.dto.StudentSubmissionDTO;
import com.zym.submit.entity.Student;

import java.util.List;

/**
 * @author zym
 * @date 2019-09-08-15:21
 */
public interface StudentExtMapper {

    //通过学生学号查询
    Student selectByStudentNumber(String studentNumber);

    //已提交实验报告的学生信息
    List<StudentSubmissionDTO> SubmittedStudentInfo(Integer taskId);
}
