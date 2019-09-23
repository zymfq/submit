package com.zhy.submit.teacher.service;

import com.zhy.submit.teacher.dto.AverageScoreOfCourseDTO;
import com.zhy.submit.teacher.dto.StudentSubmissionDTO;
import com.zhy.submit.teacher.dto.showReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.PrimitiveIterator;

@Service
@Transactional
public interface scoreDetailService {
    //班级平均分
    public List<showReportDTO> AverageScore(String teacherNumber, int currPage, int pageSize);

    //班级学生成绩详情
    public List<StudentSubmissionDTO> ScoreDetail(String taskId);

    //按班级显示老师所有课程
    public List<AverageScoreOfCourseDTO> showByCourse(String teacherNumber);

    //班级学生每门课程所有实验平均分
    public List<StudentSubmissionDTO> AllExperimentAverageScore(int classId,int courseId,int termId);




}
