package com.zym.submit.controller;

import com.zym.submit.dto.ReportDTO;
import com.zym.submit.dto.TaskDTO;
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
public class ReportController {

    @Autowired
    private ReportService reportService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    /**
     * 本地测试提交实验报告
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadtest")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        try {
            String fileName = file.getOriginalFilename();
            //String filePath = "F:\\Test";
            File filePath = new File("F:\\Test");
            if(!filePath.exists()  && !filePath.isDirectory()){
                filePath.mkdir();
            }
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

    /**
     * 查询所有已提交试验报告
     * @param studentNumber
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public CommonReturnType listReport(@RequestParam(name = "studentNumber",defaultValue = "001") String studentNumber,
                                       @RequestParam(name = "page" ,defaultValue = "1")Integer pageNum,
                                       @RequestParam(name = "limit",defaultValue = "5")Integer pageSize){

        System.out.println("pageNUm"+pageNum);
        System.out.println(pageSize);
        List<ReportDTO> reportDTOList = reportService.listReport(studentNumber,pageNum, pageSize);
        return CommonReturnType.okOf(reportDTOList);
    }

    /**
     * 查询所有已提交试验报告
     * @param studentNumber
     * @return
     */
    @GetMapping("/listall")
    @ResponseBody
    public CommonReturnType listAllReport(@RequestParam(name = "studentNumber") String studentNumber,
                                       @RequestParam(name = "page")Integer pageNum,
                                       @RequestParam(name = "limit")Integer pageSize){

        System.out.println("pageNUm"+pageNum);
        System.out.println(pageSize);
        List<ReportDTO> reportDTOList = reportService.listReport(studentNumber,pageNum, pageSize);
        return CommonReturnType.okOf(reportDTOList);
    }

    /**
     * 根据课程查询已提交实验报告
     * @param studentNumber
     * @param termId
     * @param courseId
     * @return
     */
    @GetMapping("/list_by_course_id")
    @ResponseBody
    public CommonReturnType listReportByCourseName(@RequestParam(name = "studentNumber")String studentNumber,
                                                   @RequestParam(name = "termId") Integer termId,
                                                   @RequestParam(name = "courseId") Integer courseId,
                                                   @RequestParam(name = "pageNum")Integer pageNum,
                                                   @RequestParam(name = "pageSize")Integer pageSize){

        List<ReportDTO> reportList = reportService.listReportByCourseId(studentNumber, termId,courseId,pageNum, pageSize);
        return CommonReturnType.okOf(reportList);
    }

    /**
     * 查询全部未提交实验报告
     * @param studentNumber
     * @param termId
     * @param classId
     * @return
     */
    @GetMapping("list_all_not_submit")
    @ResponseBody
    public CommonReturnType listAllByNotSubmit(@RequestParam(name = "studentNumber")String studentNumber,
                                               @RequestParam(name = "termId") Integer termId,
                                               @RequestParam(name = "classId") Integer classId,
                                               @RequestParam(name = "pageNum")Integer pageNum,
                                               @RequestParam(name = "pageSize")Integer pageSize){

        List<TaskDTO> reportList = reportService.listAllNotSubmit(studentNumber, termId, classId,pageNum, pageSize);
        return CommonReturnType.okOf(reportList);
    }



    @GetMapping("list_not_submit")
    @ResponseBody
    public CommonReturnType listReportByNotSubmit(@RequestParam(name = "studentNumber")String studentNumber,
                                                  @RequestParam(name = "classId") Integer classId){

        List<TaskDTO> reportList = reportService.listNotSubmit(studentNumber,classId);
        return CommonReturnType.okOf(reportList);
    }

    /**
     * 撤回试验报告
     * @param taskId
     * @return
     */
    @GetMapping("roll_back")
    @ResponseBody
    public CommonReturnType rollBackReport(@RequestParam(name = "taskId") Integer taskId){
        int effectNumber = reportService.rollBackReport(taskId);
        return CommonReturnType.okOf(effectNumber );
    }

    /**
     * 提交试验报告
     * @param file
     * @param studentNumber
     * @param taskId
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/upload2")
    @ResponseBody
    public CommonReturnType upload2(@RequestParam("file") MultipartFile file,
                                    @RequestParam(name = "studentNumber")String studentNumber,
                                    @RequestParam(name = "taskId") Integer taskId,
                                    HttpServletRequest request,
                                    HttpServletResponse response
    ){

        Map<String,Object> fileMap;
        fileMap = reportService.upload(file, taskId, studentNumber, request, response);
        return  CommonReturnType.okOf(fileMap.get("path"));
    }

    /**
     * 下载实验报告
     * @param taskId
     * @param response
     * @throws IOException
     */
    //打包下载
    @GetMapping("/download")
    @ResponseBody
    public CommonReturnType download(@RequestParam("taskId")Integer taskId,HttpServletResponse response) {

        boolean b = reportService.downloadZip(taskId, response);

        System.out.println("下载成功");
        return CommonReturnType.okOf(b);
    }




}