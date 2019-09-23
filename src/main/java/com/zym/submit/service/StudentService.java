package com.zym.submit.service;

import com.zym.submit.dto.StudentDTO;

/**
 * @author zym
 * @date 2019-09-06-19:57
 */
public interface StudentService {

    /**
     * 学生登录
     * @param studentNumber
     * @param studentPassword
     * @return
     */
    StudentDTO login(String studentNumber, String studentPassword);

    /**
     * 学生修改密码
     * @param studentNumber
     * @param studentPassword
     * @return
     */
    StudentDTO updatePassword(String studentNumber, String studentPassword);

}
