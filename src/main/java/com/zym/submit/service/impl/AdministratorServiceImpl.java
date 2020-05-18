package com.zym.submit.service.impl;

import com.zym.submit.dto.AdministratorDTO;
import com.zym.submit.entity.Teacher;
import com.zym.submit.entity.entityExample.TeacherExample;
import com.zym.submit.exception.SubmitErrorCode;
import com.zym.submit.exception.SubmitException;
import com.zym.submit.mapper.TeacherMapper;
import com.zym.submit.service.AdministratorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * @author zym
 * @date 2019-11-14-19:31
 */
@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public AdministratorDTO login(String teacherNumber, String teacherPassword) {

        TeacherExample teacherExample = new TeacherExample();
        teacherExample.createCriteria().andTeacherNumberEqualTo(teacherNumber);
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
        if (teachers.isEmpty()){
            return null;
        }
        Teacher teacher = teachers.get(0);

        if (teacher == null || teacher.getIdentity() != 2){
            throw new SubmitException(SubmitErrorCode.ADMIN_MESSAGE_NOT_FIND);
        }

        AdministratorDTO administratorDTO = this.convertFromTeacher(teacher);

        if (!StringUtils.equals(teacherPassword, teacher.getTeacherPassword())){
            throw new SubmitException(SubmitErrorCode.USER_MESSAGE_NOT_FIND);
        }

        return administratorDTO;
    }

    @Override
    public void deleteStu() {

    }

    @Override
    public void deleteTeacher() {

    }


    private AdministratorDTO convertFromTeacher(Teacher teacher){
        if (teacher==null){
            return null;
        }
        AdministratorDTO administratorDTO = new AdministratorDTO();
        BeanUtils.copyProperties(teacher, administratorDTO);
        return administratorDTO;
    }
}
