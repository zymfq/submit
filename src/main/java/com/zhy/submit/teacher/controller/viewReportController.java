package com.zhy.submit.teacher.controller;


import com.zhy.submit.teacher.VO.ResultVO;
import com.zhy.submit.teacher.dto.StudentSubmissionDTO;
import com.zhy.submit.teacher.dto.showReportDTO;
import com.zhy.submit.teacher.service.addReportService;
import com.zhy.submit.teacher.service.viewReportService;
import com.zhy.submit.teacher.utils.ResultVOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/viewReport")
public class viewReportController {
    @Autowired
    private addReportService addReportService;
    @Autowired
    private viewReportService viewReportService;


    //按班级显示实验报告提交情况
    @GetMapping("/view")
    public ResultVO viewReport(@RequestParam("teacherNumber") String teacherNumber,@RequestParam("currPage") int currPage,@RequestParam("pageSize") int pageSize){
        List<showReportDTO> reportDTOList=addReportService.view(teacherNumber,currPage,pageSize);
        ResultVO resultVO=ResultVOUtils.success(reportDTOList);
        return  resultVO;
    }

    //查看各个班级未提交学生的名单（根据 前端传过来的taskId（实验Id）+schoolClass（班级）+grade(年级)）
    @GetMapping("/unsubmitted")
   public ResultVO unsubmitted(@RequestParam("taskId") String taskId,@RequestParam("schoolClass") String className,@RequestParam("grade") String gradeName){
      List<StudentSubmissionDTO>submissionDTOList=  viewReportService.unsubmittedStudent(taskId, className, gradeName);
      //已提交实验报告占班级总人数的比例

        //integer：班级总人数；List.size:已提交学生人数
         Integer integer=viewReportService.sumStudent(className, gradeName);
        int result=submissionDTOList.size();
      return  ResultVOUtils.success(submissionDTOList);

    }

    //查看各个班级已提交学生名单和URL
    @GetMapping("/submitted")
    public ResultVO submitted(@RequestParam("taskId") String taskId){
        List<StudentSubmissionDTO> result=viewReportService.SubmittedStudent(taskId);
        int i=1;
        for(StudentSubmissionDTO a:result){
            a.setNumber(i++);
        }
        return ResultVOUtils.success(result);
    }


    //根据URL进行在线预览
    @GetMapping("/preview")
    public void preview(@RequestParam("reportPath") String reportPath,
                        HttpServletRequest request,
                        HttpServletResponse response) throws IOException {
        viewReportService.word2PDF(request,response,reportPath);
    }


    //教师打分(参数studentNumber+score+taskId)
    @GetMapping("/mark")
    public Integer  mark(@RequestParam("score") double score,@RequestParam("studentNumber") String studentNumber,@RequestParam("taskId") String taskId){
        Integer integer=viewReportService.markStudent(score,studentNumber,taskId);
        return integer;
    }


    //打包下载
    @GetMapping("/download")
    public void download(@RequestParam("taskId")String taskId,HttpServletResponse response) throws IOException {
        viewReportService.downloadZip(taskId,response);
    }

}




