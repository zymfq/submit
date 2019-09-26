package com.zhy.submit.teacher.service;

import com.zhy.submit.teacher.dto.TeacherDTO;
import com.zhy.submit.teacher.dto.addReportDTO;
import com.zhy.submit.teacher.dto.showReportDTO;
import com.zhy.submit.teacher.entity.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.View;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface addReportService {
    //教师登录
    public TeacherDTO DoLogin(TeacherDTO teacherDTO);
    //教师上传实验模板
    public Map<String,Object> upload(MultipartFile myfiles,HttpServletRequest request,HttpServletResponse respons);
    //获取学期信息
    public Integer  getTeamId(addReportDTO addDTO);
    //获取班级ID
    public Integer getclassID(String className);
    //获取课程id
    public Integer getCourseId(String courseName);
    //添加实验报告信息
    public Integer add(addReportDTO addDTO);
    //展示教师发布的所有实验
    public List<showReportDTO> view(String teacherNumber,int currPage,int pageSize);
    public Integer viewCount(String teacherNumber);
    //删除发布的实验报告信息
    Integer cancelReport(String teacherNumber,String experimentName);


}
