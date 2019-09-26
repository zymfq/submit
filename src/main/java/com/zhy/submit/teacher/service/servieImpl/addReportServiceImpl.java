package com.zhy.submit.teacher.service.servieImpl;

import com.zhy.submit.teacher.dto.TeacherDTO;
import com.zhy.submit.teacher.dto.addReportDTO;
import com.zhy.submit.teacher.dto.showReportDTO;
import com.zhy.submit.teacher.entity.Teacher;
import com.zhy.submit.teacher.enums.ResultEnum;
import com.zhy.submit.teacher.exception.SubmitException;
import com.zhy.submit.teacher.mapper.*;
import com.zhy.submit.teacher.service.addReportService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.io.File;

@Service
@Transactional
public class addReportServiceImpl implements addReportService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private TermMapper termMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private taskNoticeMapper noticeMapper;
    //教师登录
    @Override
    public TeacherDTO DoLogin(TeacherDTO teacherDTO) {
        TeacherDTO dto= teacherMapper.TeacherLogin(teacherDTO.getTeacherNumber(),teacherDTO.getTeacherPassword());
        if(dto==null){
            throw new SubmitException(ResultEnum.account_error);
        }
        return dto;
    }
   //获取到term_id(根据学年，学期，年级)

    @Override
    public Integer getTeamId(addReportDTO addDTO) {
        return termMapper.selectTermIdByForm(addDTO.getSchoolYear(),addDTO.getTerm(),addDTO.getGrade());
    }
  //获取班级id

    @Override
    public Integer getclassID(String className) {
       return classMapper.getClassId(className);
    }
    //获取课程id

    @Override
    public Integer getCourseId(String courseName) {
        return courseMapper.selectCourseId(courseName);

    }
   //添加实验报告
    @Override
    public Integer add(addReportDTO addDTO ){

        return  noticeMapper.addTaskNotice(addDTO);
    }

    //教师所有实验报告
    @Override
    public List<showReportDTO> view(String teacherNumber,int currPage,int pageSize) {
        Map<String,Object> data=new HashMap<>();
        data.put("currIndex",(currPage-1)*pageSize);
        data.put("pageSize",pageSize);
        data.put("teacherNumber",teacherNumber);
        return noticeMapper.showTaskNotice(data);
    }

    @Override
    public Integer viewCount(String teacherNumber) {
        return  noticeMapper.showTaskNoticeCount(teacherNumber);
    }
    //删除实验报告

    @Override
    public Integer cancelReport(String teacherNumber, String experimentName) {
        return noticeMapper.deleteReport(teacherNumber, experimentName);
    }

    //上传实验报告
    @Override
    public Map<String, Object> upload(MultipartFile myfiles, HttpServletRequest request, HttpServletResponse respons) {

        Map<String,Object> resMap=new HashMap<String,Object>();
        String Filename = myfiles.getOriginalFilename();
        String suffix = Filename.substring(Filename.lastIndexOf(".") + 1);
        if (!suffix .equals("doc") &&! suffix.equals("docx")) {
            resMap.put("msg", "请选择.doc或.docx文件");
            throw new SubmitException(ResultEnum.format_error);

        }
        if (myfiles.getSize() > 1024 * 1024 * 20) {
            resMap.put("code", 500);
            resMap.put("msg", "文件过大，请上传20M以内的文件");
            System.out.println("文件上传失败");
            throw new SubmitException(ResultEnum.file_size_error);

        }
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        String realPath = "d://uploadFiles";

        try {
            FileUtils.copyInputStreamToFile(myfiles.getInputStream(), new File(realPath,Filename));
            resMap.put("code", "200");
            resMap.put("msg", "上传成功");
            resMap.put("filename", Filename);
            String endPath = basePath + "/static/image/" + Filename;
            java.net.URLEncoder.encode(endPath, "UTF-8");
            resMap.put("path", endPath);
            System.out.println(resMap.get("path").toString());

        } catch (IOException e) {
            resMap.put("code", "500");
            System.out.println("文件上传失败");
            resMap.put("msg", "文件上传失败");
            e.printStackTrace();
        }
        return resMap;
    }
}
