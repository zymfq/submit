package com.zhy.submit.teacher.mapper;

import com.zhy.submit.teacher.dto.AverageScoreOfCourseDTO;
import com.zhy.submit.teacher.dto.addReportDTO;
import com.zhy.submit.teacher.dto.showReportDTO;
import com.zhy.submit.teacher.entity.taskNotice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface taskNoticeMapper {
    //添加实验报告
    Integer addTaskNotice(addReportDTO addDTO);
    //查询所有实验报告
    List<showReportDTO> showTaskNotice(Map<String,Object> data);
    Integer showTaskNoticeCount(String teacherNumber );
    //删除实验报告
    Integer deleteReport(String teacherNumber,String experimentName);
    int deleteByPrimaryKey(Integer id);

    //获取下载压缩文件的名字
    showReportDTO getZipName(String taskId);

    //按班级展示老师所有课程
    List<AverageScoreOfCourseDTO> viewByCourse(String teacherNumber);
    Integer viewByCourseCount(String teacherNumber);
    int insert(taskNotice record);

    int insertSelective(taskNotice record);

    taskNotice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(taskNotice record);

    int updateByPrimaryKey(taskNotice record);
}