package com.zym.submit.mapper;

import com.zym.submit.entity.Student;

/**
 * @author zym
 * @date 2019-09-08-15:21
 */
public interface StudentExtMapper {

    //通过学生学号查询
    Student selectByStudentNumber(String studentNumber);
}
