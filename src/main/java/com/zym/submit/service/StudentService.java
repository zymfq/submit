package com.zym.submit.service;

import com.zym.submit.dto.StudentDTO;

/**
 * @author zym
 * @date 2019-09-06-19:57
 */
public interface StudentService {

    StudentDTO login(String studentNumber, String studentPassword);

    StudentDTO updatePassword(String studentNumber, String studentPassword);



}
