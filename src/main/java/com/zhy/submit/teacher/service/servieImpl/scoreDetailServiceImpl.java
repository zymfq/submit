package com.zhy.submit.teacher.service.servieImpl;

import com.zhy.submit.teacher.dto.AverageScoreOfCourseDTO;
import com.zhy.submit.teacher.dto.StudentSubmissionDTO;
import com.zhy.submit.teacher.dto.showReportDTO;
import com.zhy.submit.teacher.entity.Student;
import com.zhy.submit.teacher.mapper.ReportMapper;
import com.zhy.submit.teacher.mapper.StudentMapper;
import com.zhy.submit.teacher.mapper.taskNoticeMapper;
import com.zhy.submit.teacher.service.scoreDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class scoreDetailServiceImpl implements scoreDetailService {
    @Autowired
    private taskNoticeMapper noticeMapper;
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private StudentMapper studentMapper;

    //查询班级平均分
    @Override
    public List<showReportDTO> AverageScore(String teacherNumber,int currPage,int pageSize) {
        Map<String,Object> data=new HashMap<>();
        data.put("currIndex",(currPage-1)*pageSize);
        data.put("pageSize",pageSize);
        data.put("teacherNumber",teacherNumber);
        List<showReportDTO> showReportDTOList=noticeMapper.showTaskNotice(data);
        //遍历List,取出每条记录中的taskId,className,gradeName
        for(showReportDTO list:showReportDTOList){
            list.setAverageScore(reportMapper.classAverageScore(list.getTaskId()));
        }
        return showReportDTOList;

    }

    //班级学生成绩详情
    @Override
    public List<StudentSubmissionDTO> ScoreDetail(String taskId) {
        return studentMapper.personalScoreDetail(taskId);
    }

    //按班级显示老师所有课程
    @Override
    public List<AverageScoreOfCourseDTO> showByCourse(String teacherNumber) {
       return noticeMapper.viewByCourse(teacherNumber);
    }

    //班级学生每门课程所有实验平均分

    @Override
    public List<StudentSubmissionDTO> AllExperimentAverageScore(int classId, int courseId, int termId) {
        return studentMapper.AllReportAverageScore(classId, courseId, termId);
    }
}
