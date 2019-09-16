package com.zym.submit.service.impl;

import com.zym.submit.dto.ReportDTO;
import com.zym.submit.dto.StudentDTO;
import com.zym.submit.dto.TaskDTO;
import com.zym.submit.entity.Report;
import com.zym.submit.entity.Student;
import com.zym.submit.entity.TaskNotice;
import com.zym.submit.entity.entityExample.ReportExample;
import com.zym.submit.exception.SubmitErrorCode;
import com.zym.submit.exception.SubmitException;
import com.zym.submit.mapper.*;
import com.zym.submit.service.ReportService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zym
 * @date 2019-09-08-20:45
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private StudentExtMapper studentExtMapper;


    @Autowired
    private ReportExtMapper reportExtMapper;

    @Autowired
    private TaskNoticeExtMapper taskNoticeExtMapper;


    @Override
    public List<ReportDTO> listReport(String studentNumber) {
        ReportExample reportExample = new ReportExample();
        reportExample.createCriteria().andStudentNumberEqualTo(studentNumber);
        reportExample.setOrderByClause("create_time desc");

        List<Report> reportList = reportMapper.selectByExample(reportExample);

        if (reportList.size() == 0) {
            return new ArrayList<>();
        }

        List<ReportDTO> reportDTOList = new ArrayList<>();

        for (Report report : reportList) {
            Student student = studentExtMapper.selectByStudentNumber(studentNumber);
            StudentDTO studentDTO = new StudentDTO();
            BeanUtils.copyProperties(student, studentDTO);
            ReportDTO reportDTO = new ReportDTO();
            BeanUtils.copyProperties(report, reportDTO);
            reportDTO.setStudentDTO(studentDTO);
            reportDTOList.add(reportDTO);
        }
        return reportDTOList;
    }

    @Override
    public List<Report> listReportByCourseId(String studentNumber, Integer termId, Integer courseId) {

        List<Report> reportList = new ArrayList<>();
        List<ReportDTO> reportDTOList = reportExtMapper.selectByCourseId(termId, courseId);
        for (ReportDTO reportDTO : reportDTOList) {
            Report report = new Report();
            BeanUtils.copyProperties(reportDTO, report);
            reportList.add(report);
        }

        return reportList;
    }

    @Override
    public List<TaskDTO> listNotSubmit(String studentNumber, Integer termId, Integer courseId) {

        List<TaskNotice> taskNoticeList = reportExtMapper.selectNotSubmit(termId, courseId);
        /*TaskDTO taskDTOList = new TaskDTO();*/
        List<TaskDTO> taskDTOList = taskNoticeList.stream().map(taskNotice -> {
            TaskDTO taskDTO = new TaskDTO();
            BeanUtils.copyProperties(taskNotice, taskDTO);
            return taskDTO;
        }).collect(Collectors.toList());

        return taskDTOList;
    }

    @Override
    public Map<String, Object> upload(MultipartFile myFiles, Integer taskId,String studentNumber,
                                      HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> resMap = new HashMap<>();

        if (myFiles.isEmpty()) {
            throw new SubmitException(SubmitErrorCode.FILE_NOT_FIND);
        }

        String Filename = myFiles.getOriginalFilename();
        String suffix = Filename.substring(Filename.lastIndexOf(".") + 1);

        if (!suffix.equals("doc") && !suffix.equals("docx")) {
            resMap.put("msg", "请选择.doc或.docx文件");
            throw new SubmitException(SubmitErrorCode.FORMAT_ERROR);
            //return resMap;
        }

        if (myFiles.getSize() > 1024 * 1024 * 20) {
            resMap.put("code", 500);
            resMap.put("msg", "文件过大，请上传20M以内的文件");
            System.out.println("文件上传失败");
            throw new SubmitException(SubmitErrorCode.FILE_SIZE_ERROR);
            //return new ModelAndView("success", resMap);
        }

        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        String realPath = "F://Test//uploadFiles/";

        try {
            FileUtils.copyInputStreamToFile(myFiles.getInputStream(), new File(realPath, Filename));
            resMap.put("code", "200");
            resMap.put("msg", "上传成功");
            resMap.put("filename", Filename);
            String endPath = basePath + "/static/image/" + Filename;
            URLEncoder.encode(endPath, "UTF-8");

            resMap.put("path", endPath);
            System.out.println(resMap.get("path").toString());

            //将上传文件的URL插入数据库
            Report report = new Report();
            TaskNotice taskNotice = taskNoticeExtMapper.selectByTaskId(taskId);
            BeanUtils.copyProperties(taskNotice, report);
            report.setStudentNumber(studentNumber);
            report.setReportName(taskNotice.getTaskName());
            report.setReportPath(endPath);
            reportMapper.insertSelective(report);

            //return resMap;
        } catch (IOException e) {

            e.printStackTrace();
            System.out.println("文件上传失败");
            resMap.put("msg", "文件上传失败");

        }
        return resMap;
    }
}
