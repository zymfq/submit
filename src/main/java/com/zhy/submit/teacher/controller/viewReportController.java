package com.zhy.submit.teacher.controller;


import com.zhy.submit.teacher.VO.ResultVO;
import com.zhy.submit.teacher.dto.StudentSubmissionDTO;
import com.zhy.submit.teacher.dto.showReportDTO;
import com.zhy.submit.teacher.enums.ResultEnum;
import com.zhy.submit.teacher.exception.SubmitException;
import com.zhy.submit.teacher.service.addReportService;
import com.zhy.submit.teacher.service.viewReportService;
import com.zhy.submit.teacher.utils.ResultVOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
//解除跨域访问的限制
@CrossOrigin(origins = "*")
@RequestMapping("/viewReport")
public class viewReportController {
    @Autowired
    private addReportService addReportService;
    @Autowired
    private viewReportService viewReportService;


    //按班级显示实验报告提交情况
    @GetMapping("/view")
    @ResponseBody
    //@RequiresPermissions("submit:viewReport:view")
    public ResultVO viewReport(@RequestParam("page") int currPage,@RequestParam("limit") int pageSize,@RequestParam("teacherNumber") String teacherNumber){
        List<showReportDTO> reportDTOList=addReportService.view(teacherNumber,currPage,pageSize);
        Integer total=addReportService.viewCount(teacherNumber);
        ResultVO resultVO=ResultVOUtils.success(total,reportDTOList);
        return  resultVO;
    }

    //查看各个班级未提交学生的名单（根据 前端传过来的taskId（实验Id）+schoolClass（班级）+grade(年级)）
    @GetMapping("/unsubmitted")
    @ResponseBody
   public ResultVO unsubmitted(@RequestParam("taskId") String taskId,@RequestParam("schoolClass") String className,@RequestParam("grade") String gradeName){
      List<StudentSubmissionDTO>submissionDTOList=  viewReportService.unsubmittedStudent(taskId, className, gradeName);
      //已提交实验报告占班级总人数的比例

        //integer：班级总人数；total:未提交学生
         Integer integer=viewReportService.sumStudent(className, gradeName);
        Integer total=viewReportService.unsubmittedStudentCount(taskId, className, gradeName);
      return  ResultVOUtils.success(total,submissionDTOList);

    }

    //查看各个班级已提交学生名单和URL
    @GetMapping("/submitted")
    @ResponseBody
    public ResultVO submitted(@RequestParam("taskId") String taskId){
        List<StudentSubmissionDTO> result=viewReportService.SubmittedStudent(taskId);
        int i=1;
        for(StudentSubmissionDTO a:result){
            a.setNumber(i++);
        }
        Integer total=viewReportService.SubmittedStudentCount(taskId);
        return ResultVOUtils.success(total,result);
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
    public ResultVO  mark(@RequestParam("score") double score,@RequestParam("studentNumber") String studentNumber,@RequestParam("taskId") String taskId){
        if(score<0||score>100)
            throw new SubmitException(ResultEnum.score_format_error);
        Integer integer= null;
        try {
            integer = viewReportService.markStudent(score,studentNumber,taskId);
        } catch (Exception e) {
            throw new SubmitException(ResultEnum.mark_fail);
        }
        return ResultVOUtils.success(0,integer);
    }


    //打包下载
    @GetMapping("/download")
    public void download(@RequestParam("taskId")String taskId,HttpServletResponse response) throws IOException {
        try {
            viewReportService.downloadZip(taskId,response);
        } catch (IOException e) {
            throw new SubmitException(ResultEnum.download_fail);
        }
    }

}




