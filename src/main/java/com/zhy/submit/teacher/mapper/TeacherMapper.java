package com.zhy.submit.teacher.mapper;

import com.zhy.submit.teacher.dto.TeacherDTO;
import com.zhy.submit.teacher.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TeacherMapper {
    TeacherDTO TeacherLogin(String teacher_number, String teacher_password);
    int deleteByPrimaryKey(Integer teacherId);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(Integer teacherId);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);
}