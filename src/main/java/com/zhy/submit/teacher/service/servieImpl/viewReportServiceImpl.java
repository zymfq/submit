package com.zhy.submit.teacher.service.servieImpl;


import com.zhy.submit.teacher.dto.StudentSubmissionDTO;
import com.zhy.submit.teacher.dto.showReportDTO;
import com.zhy.submit.teacher.mapper.ReportMapper;
import com.zhy.submit.teacher.mapper.StudentMapper;
import com.zhy.submit.teacher.mapper.taskNoticeMapper;
import com.zhy.submit.teacher.service.addReportService;
import com.zhy.submit.teacher.service.viewReportService;
import org.apache.commons.io.IOUtils;
import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Transactional
public class viewReportServiceImpl implements viewReportService {
    @Autowired
    taskNoticeMapper noticeMapper;
    @Autowired
    private DocumentConverter documentConverter;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    ReportMapper reportMapper;

    //打分
    @Override
    public Integer markStudent(double score,String studentNumber,String taskId) {
        return  reportMapper.addScore(score,studentNumber,taskId);
    }

    //班级学生总人数
    @Override
    public Integer sumStudent(String className, String gradeName) {
        return  studentMapper.totalStudentNumber(className, gradeName);
    }

    //未提交实验报告的学生信息
    @Override
    public List<StudentSubmissionDTO> unsubmittedStudent(String taskId, String className, String gradeName) {
        return studentMapper.unsubmitStudentInfo(taskId, className, gradeName);
    }

    //已提交实验报告的学生信息
    @Override
    public List<StudentSubmissionDTO> SubmittedStudent(String taskId, String className, String gradeName) {
        return  studentMapper.SubmittedStudentInfo(taskId, className, gradeName);
    }




    //将上传的Word文档转成PDF,通过输出流响应到页面
    @Override
    public void word2PDF(HttpServletRequest request, HttpServletResponse response,String url) throws IOException {
        //String url="http://localhost:8080/submit/static/image/2a27c1b6_2016207320037-王乐- 图形点阵显示实验.doc";
        String wordName=url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."));

        //得到文件后缀名
        String suffix=url.substring(url.lastIndexOf(".")+1);
        File inputFile=null;
        if(suffix.equals("doc")){
           inputFile=new File("d:/uploadFiles/"+wordName+".doc");
        }else {
            inputFile=new File("d:/uploadFiles/"+wordName+".docx");
        }
        File outputFile=new File("d:/previewPDF/"+wordName+".pdf");
        try {
            documentConverter.convert(inputFile).to(outputFile).execute();
            ServletOutputStream outputStream=response.getOutputStream();
            InputStream in=new FileInputStream(new File("d:/previewPDF/"+wordName+".pdf"));
            int i= IOUtils.copy(in,outputStream);
            in.close();
            outputStream.close();
        }catch (OfficeException e){
            e.printStackTrace();
            System.out.println("转换出错");
        }
    }


}
