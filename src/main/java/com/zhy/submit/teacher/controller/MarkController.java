package com.zhy.submit.teacher.controller;

import com.zhy.submit.teacher.VO.ResultVO;
import com.zhy.submit.teacher.dto.AverageScoreOfCourseDTO;
import com.zhy.submit.teacher.dto.StudentSubmissionDTO;
import com.zhy.submit.teacher.dto.showReportDTO;
import com.zhy.submit.teacher.service.scoreDetailService;
import com.zhy.submit.teacher.utils.ResultVOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/mark")
public class MarkController {
    @Autowired
    scoreDetailService scoreDetailService;

    //show班级平均分
    @GetMapping("/average")
    public ResultVO average(@RequestParam("teacherNumber") String teacherNumber,@RequestParam("currPage") int currPage,@RequestParam("pageSize") int pageSize){
        List<showReportDTO> list= scoreDetailService.AverageScore(teacherNumber,currPage,pageSize);
        ResultVO res= ResultVOUtils.success(list);
        return  res;
    }

    //班级学生每门课程每门实验成绩详情
    @GetMapping("/detail")
    public ResultVO detail(@RequestParam("taskId") String taskId){
        List<StudentSubmissionDTO> list=scoreDetailService.ScoreDetail(taskId);
        ResultVO resultVO=ResultVOUtils.success(list);
        return resultVO;

    }


    //按班级显示老师所有课程
    @GetMapping("/course")
    public ResultVO course(@RequestParam("teacherNumber") String teacherNumber){
        List<AverageScoreOfCourseDTO> res=scoreDetailService.showByCourse(teacherNumber);
        return ResultVOUtils.success(res);
    }
    //班级学生每门课程所有实验平均分
    @GetMapping("/AllAverage")
    public ResultVO AllAverage(@RequestParam("classId") int classId,@RequestParam("courseId") int courseId,@RequestParam("termId") int termId){
        List<StudentSubmissionDTO> res=scoreDetailService.AllExperimentAverageScore(classId, courseId, termId);
        return  ResultVOUtils.success(res);
    }



}
