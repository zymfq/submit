package com.zhy.submit.teacher.mapper;

import com.zhy.submit.teacher.dto.StudentSubmissionDTO;
import com.zhy.submit.teacher.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Mapper
@Repository
public interface StudentMapper {

    //查询班级总人数
    Integer totalStudentNumber(String className,String gradeName);
    //未提交实验报告的学生信息
    List<StudentSubmissionDTO> unsubmitStudentInfo(String taskId,String className,String gradeName);

    //已提交实验报告的学生信息
    List<StudentSubmissionDTO> SubmittedStudentInfo(String taskId);

    //班级学生成绩详情
    List<StudentSubmissionDTO> personalScoreDetail(String taskId);

    //班级学生每门课程所有实验平均分
    List<StudentSubmissionDTO> AllReportAverageScore(int classId,int courseId,int termId);
    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer studentId);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}