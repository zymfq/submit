package com.zhy.submit.teacher.controller;

import com.zhy.submit.teacher.VO.ResultVO;
import com.zhy.submit.teacher.dto.AverageScoreOfCourseDTO;
import com.zhy.submit.teacher.dto.StudentSubmissionDTO;
import com.zhy.submit.teacher.dto.showReportDTO;
import com.zhy.submit.teacher.enums.ResultEnum;
import com.zhy.submit.teacher.exception.SubmitException;
import com.zhy.submit.teacher.service.addReportService;
import com.zhy.submit.teacher.service.scoreDetailService;
import com.zhy.submit.teacher.service.viewReportService;
import com.zhy.submit.teacher.utils.ResultVOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/mark")
public class MarkController {
    @Autowired
    scoreDetailService scoreDetailService;
    @Autowired
    addReportService addReportService;
    @Autowired
    viewReportService viewReportService;

    //show班级平均分
    @GetMapping("/average")
    @ResponseBody
    public ResultVO average(@RequestParam("currPage") int currPage,@RequestParam("pageSize") int pageSize,@RequestParam("teacherNumber") String teacherNumber){
        List<showReportDTO> list= null;
        try {
            list = scoreDetailService.AverageScore(teacherNumber,currPage,pageSize);
        } catch (Exception e) {
            throw  new SubmitException(ResultEnum.show_error);
        }
        Integer total=addReportService.viewCount(teacherNumber);
        return  ResultVOUtils.success(total,list);

    }

    //班级学生每门课程每门实验成绩详情
    @GetMapping("/detail")
    @ResponseBody
    public ResultVO detail(@RequestParam("taskId") String taskId){
        List<StudentSubmissionDTO> list= null;
        try {
            list = scoreDetailService.ScoreDetail(taskId);
        } catch (Exception e) {
            throw new SubmitException(ResultEnum.show_error);
        }
        Integer total= viewReportService.SubmittedStudentCount(taskId);
        ResultVO resultVO=ResultVOUtils.success(total,list);

        return resultVO;

    }


    //按班级显示老师所有课程
    @GetMapping("/course")
    @ResponseBody
    public ResultVO course(@RequestParam("teacherNumber") String teacherNumber){
        List<AverageScoreOfCourseDTO> res= null;
        try {
            res = scoreDetailService.showByCourse(teacherNumber);
        } catch (Exception e) {
            throw new SubmitException(ResultEnum.show_error);
        }
        Integer total=scoreDetailService.showByCourseCount(teacherNumber);
        return ResultVOUtils.success(4,res);
    }
    //班级学生每门课程所有实验平均分
    @GetMapping("/AllAverage")
    @ResponseBody
    public ResultVO AllAverage(@RequestParam("classId") int classId,@RequestParam("courseId") int courseId,@RequestParam("termId") int termId){
        List<StudentSubmissionDTO> res= null;
        try {
            res = scoreDetailService.AllExperimentAverageScore(classId, courseId, termId);
        } catch (Exception e) {
            throw new SubmitException(ResultEnum.show_error);
        }
        Integer total=scoreDetailService.AllExperimentAverageScoreCount(classId, courseId, termId);
        return  ResultVOUtils.success(total,res);
    }
    //

}
