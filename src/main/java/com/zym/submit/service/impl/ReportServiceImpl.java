package com.zym.submit.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.zym.submit.dto.DownloadReportDTO;
import com.zym.submit.dto.ReportDTO;
import com.zym.submit.dto.StudentSubmissionDTO;
import com.zym.submit.dto.TaskDTO;
import com.zym.submit.entity.Class;
import com.zym.submit.entity.*;
import com.zym.submit.entity.entityExample.ReportExample;
import com.zym.submit.entity.entityExample.TaskNoticeExample;
import com.zym.submit.entity.entityExample.TeacherExample;
import com.zym.submit.enums.TermTypeEnum;
import com.zym.submit.exception.SubmitErrorCode;
import com.zym.submit.exception.SubmitException;
import com.zym.submit.mapper.*;
import com.zym.submit.service.ReportService;
import com.zym.submit.utils.DownloadZipUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private TermMapper termMapper;

    @Autowired
    private ReportExtMapper reportExtMapper;

    @Autowired
    private TaskNoticeMapper taskNoticeMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private StudentExtMapper studentExtMapper;

    @Autowired
    private TaskNoticeExtMapper taskNoticeExtMapper;

    @Autowired
    //   private RedisTemplate<Object, Object> redisTemplate;
    private RedisTemplate<Object, Object> JsonRedisTemplate;

    @Override
    public List<ReportDTO> listReport(String studentNumber, Integer pageNum, Integer pageSize) {

        //字符串的序列化器
//        RedisSerializer redisSerializer = new StringRedisSerializer();
//        JsonRedisTemplate.setKeySerializer(redisSerializer);

        List<ReportDTO> reports = (List<ReportDTO>) JsonRedisTemplate.opsForValue().get(studentNumber + pageNum);

        if (reports == null) {
            System.out.println("查询的数据库.............");
            ReportExample reportExample = new ReportExample();
            reportExample.createCriteria().andStudentNumberEqualTo(studentNumber);
            reportExample.setOrderByClause("create_time desc");

            PageHelper.startPage(pageNum, pageSize);
            List<Report> reportList = reportMapper.selectByExample(reportExample);
            if (reportList.size() == 0) {
                return new ArrayList<>();
            }
            List<ReportDTO> reportDTOList = new ArrayList<>();


            for (Report report : reportList) {
                ReportDTO reportDTO = new ReportDTO();
                BeanUtils.copyProperties(report, reportDTO);

                TaskNotice taskNotice = taskNoticeMapper.selectByPrimaryKey(report.getTaskId());

                String teacherName = teacherMapper.selectByTeacherNumber(taskNotice.getTeacherNumber());
                Course course = courseMapper.selectByPrimaryKey(taskNotice.getCourseId());
                reportDTO.setCourseName(course.getCourseName());
                reportDTO.setTeacherName(teacherName);
                reportDTOList.add(reportDTO);
            }
            //String value = JSON.toJSONString(reportDTOList);
            JsonRedisTemplate.opsForValue().set(studentNumber + pageNum, reportDTOList);
            //reports.addAll(reportDTOList);
            reports = reportDTOList.stream().map(e -> JSON.
                    parseObject(JSON.toJSONString(e), ReportDTO.class)
            ).collect(Collectors.toList());
        } else {
            System.out.println("查询的缓存111111111111");
        }
        return reports;
    }


    @Override
    public List<ReportDTO> listReportByCourseId(String studentNumber, Integer termId, Integer courseId,
                                                Integer pageNum, Integer pageSize) {

        this.checkTerm_Course(termId, courseId);

        TaskNoticeExample taskNoticeExample = new TaskNoticeExample();
        Course course = courseMapper.selectByPrimaryKey(courseId);
        taskNoticeExample.createCriteria().andTermIdEqualTo(termId).andCourseIdEqualTo(courseId);
        List<TaskNotice> taskNotices = taskNoticeMapper.selectByExample(taskNoticeExample);
        List<Integer> list = new ArrayList<>();
        List<String> teacherNumList = new ArrayList<>();

        for (TaskNotice taskNotice : taskNotices) {
            list.add(taskNotice.getTaskId());
            String teacherNumber = taskNotice.getTeacherNumber();
            String name = teacherMapper.selectByTeacherNumber(teacherNumber);
            teacherNumList.add(name);

        }

        TeacherExample teacherExample = new TeacherExample();
        teacherExample.createCriteria().andTeacherIdIn(list);

        ReportExample reportExample = new ReportExample();
        reportExample.createCriteria().andStudentNumberEqualTo(studentNumber).andTaskIdIn(list);

        PageHelper.startPage(pageNum, pageSize);
        List<Report> reportList = reportMapper.selectByExample(reportExample);

        /*List<ReportDTO> reportDTOList = reportList.stream().map(report -> {
            ReportDTO reportDTO = new ReportDTO();
            BeanUtils.copyProperties(report, reportDTO);
            reportDTO.setCourseName(course.getCourseName());
           // reportDTO.setTeacherName();
            return reportDTO;
        }).collect(Collectors.toList());*/

        List<ReportDTO> result = new ArrayList<>();
        for (int i = 0; i < reportList.size(); i++) {
            ReportDTO reportDTO = new ReportDTO();
            BeanUtils.copyProperties(reportList.get(i), reportDTO);
            reportDTO.setCourseName(course.getCourseName());
            reportDTO.setTeacherName(teacherNumList.get(i));
            result.add(reportDTO);
        }
        return result;
    }

    /**
     * 查看全部未提交实验报告
     * @param studentNumber
     * @param termId
     * @param classId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<TaskDTO> listAllNotSubmit(String studentNumber, Integer termId, Integer classId, Integer pageNum,
                                          Integer pageSize) {

        this.checkTerm_Class(termId, classId);

        PageHelper.startPage(pageNum, pageSize);
        List<TaskNotice> taskNoticeList = reportExtMapper.selectAllNotSubmit(studentNumber, termId, classId);

        if (taskNoticeList == null) {
            throw new SubmitException(SubmitErrorCode.ALL_REPORT_IS_SUBMIT);
        }

        List<TaskDTO> taskDTOList = taskNoticeList.stream().map(taskNotice -> {
            TaskDTO taskDTO = new TaskDTO();
            Course course = courseMapper.selectByPrimaryKey(taskNotice.getCourseId());
            BeanUtils.copyProperties(taskNotice, taskDTO);

            taskDTO.setCourseName(course.getCourseName());
            String teacherNumber = teacherMapper.selectByTeacherNumber(taskNotice.getTeacherNumber());
            taskDTO.setTeacherName(teacherNumber);

            Term term = termMapper.selectByPrimaryKey(termId);
            taskDTO.setStudyYear(term.getStudyYear());
            taskDTO.setTermName(TermTypeEnum.nameOfType(term.getTermId()));

            return taskDTO;
        }).collect(Collectors.toList());

        return taskDTOList;
    }

    /**
     * 用hashMap实现
     *
     * @param studentNumber
     * @param classId
     * @return
     */
    @Override
    public List<TaskDTO> listNotSubmit(String studentNumber, Integer classId) {

        TaskNoticeExample taskNoticeExample = new TaskNoticeExample();
        taskNoticeExample.createCriteria().andClassIdEqualTo(classId);
        List<TaskNotice> taskNoticeList = taskNoticeMapper.selectByExample(taskNoticeExample);

        ReportExample reportExample = new ReportExample();
        reportExample.createCriteria().andStudentNumberEqualTo(studentNumber);
        List<Report> reportList = reportMapper.selectByExample(reportExample);

        Map<Integer, String> taskNoticeMap = new HashMap<>();
        for (TaskNotice taskNotice : taskNoticeList) {
            taskNoticeMap.put(taskNotice.getTaskId(), taskNotice.getTaskName());
        }

        for (Report report : reportList) {
            if (taskNoticeMap.containsKey(report.getTaskId())) {
                taskNoticeMap.remove(report.getTaskId());
            }
        }

        TaskDTO taskDTO = new TaskDTO();
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : taskNoticeMap.entrySet()) {

            TaskNotice taskNotice = taskNoticeMapper.selectByPrimaryKey(entry.getKey());
            BeanUtils.copyProperties(taskNotice, taskDTO);

            Course course = courseMapper.selectByPrimaryKey(taskNotice.getCourseId());
            taskDTO.setCourseName(course.getCourseName());

            String teacherNumber = teacherMapper.selectByTeacherNumber(taskNotice.getTeacherNumber());
            taskDTO.setTeacherName(teacherNumber);

            Term term = termMapper.selectByPrimaryKey(taskNotice.getTermId());
            taskDTO.setStudyYear(term.getStudyYear());
            taskDTO.setTermName(TermTypeEnum.nameOfType(term.getTermId()));
            taskDTOList.add(taskDTO);
        }

        return taskDTOList;
    }

    /**
     * 实验报告撤回功能
     *
     * @param taskId
     * @return
     */
    @Override
    public int rollBackReport(Integer taskId) {

        ReportExample reportExample = new ReportExample();
        reportExample.createCriteria().andTaskIdEqualTo(Collections.singletonList(taskId));

        TaskNotice taskNotice = taskNoticeMapper.selectByPrimaryKey(taskId);

        if (taskNotice == null) {
            throw new SubmitException(SubmitErrorCode.FILE_IS_MISS);
        }

        Date submitDeadline = taskNotice.getSubmitDeadline();

        if (submitDeadline.after(new Date())) {
            throw new SubmitException(SubmitErrorCode.SUBMIT_TIME_OVER);
        }

        int effectNumber = reportMapper.deleteByExample(reportExample);

        return effectNumber;
    }

    /**
     * 实验报告上传功能
     *
     * @param myFiles
     * @param taskId
     * @param studentNumber
     * @param request
     * @param response
     * @return
     */
    @Override
    public Map<String, Object> upload(MultipartFile myFiles, Integer taskId, String studentNumber,
                                      HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> resMap = new HashMap<>();

        if (myFiles.isEmpty()) {
            throw new SubmitException(SubmitErrorCode.FILE_NOT_FIND);
        }

        ReportExample reportExample = new ReportExample();
        reportExample.createCriteria().andStudentNumberEqualTo(studentNumber).andTaskIdEqualTo(Collections.singletonList(taskId));
        List<Report> reportList = reportMapper.selectByExample(reportExample);
        if (reportList.size() != 0) {
            throw new SubmitException(SubmitErrorCode.REPEATED_SUBMIT);
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
//        File realPath = new File("F://Test//uploadFiles/");
//        if(!realPath.exists()  && !realPath.isDirectory()){
//            realPath.mkdir();
//        }
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

            //将上传文件信息插入数据库
            Report report = new Report();
            TaskNotice taskNotice = taskNoticeMapper.selectByPrimaryKey(taskId);
            BeanUtils.copyProperties(taskNotice, report);
            report.setStudentNumber(studentNumber);
            report.setReportName(taskNotice.getTaskName());
            report.setReportPath(endPath);
            reportMapper.insertSelective(report);

            //return resMap;
        } catch (IOException e) {

            e.printStackTrace();
            System.out.println("文件上传失败");
            resMap.put("message", "文件上传失败");

        }
        return resMap;
    }

    /**
     * 实验报告下载功能
     * @param taskId
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    public boolean downloadZip(Integer taskId, HttpServletResponse response) {
        //获取下载文件的URL
        List<StudentSubmissionDTO> list = studentExtMapper.SubmittedStudentInfo(taskId);
        //根据URL得到文件的绝对路径
        List<String> filePaths = new ArrayList<String>();
        for (StudentSubmissionDTO a : list) {
            String url = a.getReportPath();
            //1.得到文件名
            String wordName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
            //2.得到文件后缀名
            String suffix = url.substring(url.lastIndexOf(".") + 1);
            //3.得到文件绝对路径
            String pathName = null;
            if (suffix.equals("doc")) {
                pathName = "F://Test//uploadFiles/" + wordName + ".doc";
            } else {
                pathName = "F://Test//uploadFiles/" + wordName + ".docx";
            }

            filePaths.add(pathName);

        }
        //创建压缩文件需要的空的zip包
        String zipBasePath = "F://Test//DownloadZip/";
        //得到下载的zip文件名（学年+学期+年级+班级+课程+实验名称）
        DownloadReportDTO dDTO = taskNoticeExtMapper.getZipName(taskId);

        String zipName = dDTO.getStudyYear() + "学年" + dDTO.getTermName() + "_" +
                dDTO.getGradeName() + dDTO.getClassName() + "_" + dDTO.getCourseName() + "_" +
                dDTO.getTaskName()+ ".zip";
        DownloadZipUtils utils = new DownloadZipUtils();
        try {
            utils.downloadZip(zipBasePath, zipName, filePaths, response);
        } catch (IOException e) {
            throw new SubmitException(SubmitErrorCode.DOWNLOAD_FAIL);
        }
        return true;

    }


    public void checkTerm_Class(Integer termId, Integer classId) {

        Term term = termMapper.selectByPrimaryKey(termId);
        if (term == null) {
            throw new SubmitException(SubmitErrorCode.TERM_NOT_EXIST);
        }

        Class classInfo = classMapper.selectByPrimaryKey(classId);
        if (classInfo == null) {
            throw new SubmitException(SubmitErrorCode.CLASS_NOT_EXIST);
        }
    }

    public void checkTerm_Course(Integer termId, Integer courseId) {

        Term term = termMapper.selectByPrimaryKey(termId);
        if (term == null) {
            throw new SubmitException(SubmitErrorCode.TERM_NOT_EXIST);
        }

        Course course = courseMapper.selectByPrimaryKey(courseId);
        if (course == null) {
            throw new SubmitException(SubmitErrorCode.COURSE_NOT_EXIST);
        }
    }
}