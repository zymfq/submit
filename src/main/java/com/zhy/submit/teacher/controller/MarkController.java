package com.zhy.submit.teacher.controller;

import com.zhy.submit.teacher.VO.ResultVO;
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
    public ResultVO average(@RequestParam("teacherNumber") String teacherNumber){
        List<showReportDTO> list= scoreDetailService.AverageScore(teacherNumber);
        ResultVO res= ResultVOUtils.success(list);
        return  res;
    }

    //班级学生成绩详情
    @GetMapping("/detail")
    public ResultVO detail(@RequestParam("taskId") String taskId,@RequestParam("className") String className,@RequestParam("gradeName") String gradeName){
        List<StudentSubmissionDTO> list=scoreDetailService.ScoreDetail(taskId, className, gradeName);
        ResultVO resultVO=ResultVOUtils.success(list);
        return resultVO;

    }

}
