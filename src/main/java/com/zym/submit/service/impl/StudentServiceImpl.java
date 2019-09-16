package com.zym.submit.service.impl;

import com.zym.submit.dto.StudentDTO;
import com.zym.submit.entity.Student;
import com.zym.submit.exception.SubmitErrorCode;
import com.zym.submit.exception.SubmitException;
import com.zym.submit.mapper.StudentExtMapper;
import com.zym.submit.mapper.StudentMapper;
import com.zym.submit.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

/**
 * @author zym
 * @date 2019-09-06-20:01
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentExtMapper studentExtMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public StudentDTO login(String studentNumber, String studentPassword) {

        Student student = studentExtMapper.selectByStudentNumber(studentNumber);
        if(student == null){
            throw new SubmitException(SubmitErrorCode.STUDENT_LOGIN_FAIL);
        }

        StudentDTO studentDTO = convertFromStudent(student);

        if(!StringUtils.equals(studentPassword, studentDTO.getStudentPassword())){
            throw new SubmitException(SubmitErrorCode.STUDENT_LOGIN_FAIL);
        }
        return studentDTO;
    }

    @Override
    public StudentDTO updatePassword(String studentNumber, String studentPassword) {
        Student student = studentExtMapper.selectByStudentNumber(studentNumber);
        if(student == null){
            throw new SubmitException(SubmitErrorCode.UNKNOWN_ERROR);
        }
        student.setStudentPassword(studentPassword);
        studentMapper.updateByPrimaryKeySelective(student);

        StudentDTO studentDTO = convertFromStudent(student);

        return studentDTO;
    }


    private StudentDTO convertFromStudent(Student student){
        if(student == null){
            return null;
        }
        StudentDTO studentDTO = new StudentDTO();
        BeanUtils.copyProperties(student, studentDTO);

        return studentDTO;
    }
}
