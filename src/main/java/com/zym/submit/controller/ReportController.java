package com.zym.submit.controller;

import com.zym.submit.dto.ReportDTO;
import com.zym.submit.dto.TaskDTO;
import com.zym.submit.entity.Report;
import com.zym.submit.mapper.StudentExtMapper;
import com.zym.submit.response.CommonReturnType;
import com.zym.submit.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author zym
 * @date 2019-09-08-20:38
 */
@Controller
@Slf4j
//@RequestMapping("/qq")
public class ReportController {


    @Autowired
    private ReportService reportService;

    @Autowired
    private StudentExtMapper studentExtMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/uploadtest")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        try {
            String fileName = file.getOriginalFilename();
            String filePath = "F:\\Test";
            File newFile = new File(filePath, fileName);
            InputStream inputStream = file.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);

            //提交
            IOUtils.copy(inputStream,fileOutputStream);
            inputStream.close();
            fileOutputStream.close();
            LOGGER.info("上传成功");
            return "上传成功";
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        return "上传失败！";
    }


    @GetMapping("/list")
    @ResponseBody
    public CommonReturnType listReport(@RequestParam(name = "studentNumber") String studentNumber){

        List<ReportDTO> reportDTOList = reportService.listReport(studentNumber);

        return CommonReturnType.okOf(reportDTOList);
    }

    @GetMapping("/list_by_course_id")
    @ResponseBody
    public CommonReturnType listReportByCourseName(@RequestParam(name = "studentNumber")String studentNumber,
                                                   @RequestParam(name = "termId") Integer termId,
                                                   @RequestParam(name = "courseId") Integer courseId){
        List<Report> reportList = reportService.listReportByCourseId(studentNumber, termId,courseId);

        return CommonReturnType.okOf(reportList);
    }

    @GetMapping("list_not_submit")
    @ResponseBody
    public CommonReturnType listReportByNotSubmit(@RequestParam(name = "studentNumber")String studentNumber,
                                                  @RequestParam(name = "termId") Integer termId,
                                                  @RequestParam(name = "courseId") Integer courseId){

        List<TaskDTO> reportList = reportService.listNotSubmit(studentNumber, termId, courseId);
        return CommonReturnType.okOf(reportList);
    }

    @PostMapping("/upload2")
    @ResponseBody
    public CommonReturnType upload2(@RequestParam("file") MultipartFile file,
                                    @RequestParam(name = "studentNumber")String studentNumber,
                                    @RequestParam(name = "taskId") Integer taskId,
                                    HttpServletRequest request,
                                    HttpServletResponse response
                                    ){

        /*Student student = studentExtMapper.selectByStudentNumber(studentNumber);
        if(student == null){
            throw new SubmitException(SubmitErrorCode.STUDENT_NOT_LOGIN);
        }*/

        Map<String,Object> fileMap;
        fileMap = reportService.upload(file, taskId, studentNumber, request, response);
        return  CommonReturnType.okOf(fileMap.get("path"));
    }
}
