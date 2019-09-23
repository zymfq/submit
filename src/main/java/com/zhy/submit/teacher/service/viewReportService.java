package com.zhy.submit.teacher.service;

import com.zhy.submit.teacher.dto.StudentSubmissionDTO;
import com.zhy.submit.teacher.dto.showReportDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public interface viewReportService {
    //将上传的Word文档转成PDF,通过输出流响应到页面
    public void word2PDF(HttpServletRequest request, HttpServletResponse response,String url) throws IOException;

    //查询学生总人数
    public Integer sumStudent(String className,String gradeName);

    //未提交实验报告的学生信息
    public List<StudentSubmissionDTO> unsubmittedStudent(String taskId,String className,String gradeName);

    //已提交实验报告的学生信息（姓名+学号+path）
    public List<StudentSubmissionDTO> SubmittedStudent(String taskId);


    //打分
    public  Integer markStudent(double score,String studentNumber,String taskId);


    //打包下载
    public boolean downloadZip(String taskId,HttpServletResponse response)throws IOException;




}
