package com.zhy.submit.teacher.mapper;

import com.zhy.submit.teacher.entity.Report;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReportMapper {
    //打分
    Integer addScore(double score,String studentNumber,String taskId);

    //查询班级平均分
    Double classAverageScore(String taskId);
    int deleteByPrimaryKey(Integer id);

    int insert(Report record);

    int insertSelective(Report record);

    Report selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKey(Report record);
}