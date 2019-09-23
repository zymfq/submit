package com.zym.submit.service.impl;

import com.zym.submit.dto.ClassDTO;
import com.zym.submit.dto.CourseDTO;
import com.zym.submit.dto.ParamDTO;
import com.zym.submit.dto.TermDTO;
import com.zym.submit.entity.Class;
import com.zym.submit.entity.Course;
import com.zym.submit.entity.Term;
import com.zym.submit.mapper.ClassMapper;
import com.zym.submit.mapper.CourseMapper;
import com.zym.submit.mapper.TermMapper;
import com.zym.submit.service.ParamService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zym
 * @date 2019-09-23-17:12
 */
@Service
public class ParamServiceImpl implements ParamService {

    @Autowired
    private TermMapper termMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ClassMapper classMapper;

    @Override
    public ParamDTO findParam() {

        List<Term> termList = termMapper.findAll();

        ParamDTO paramDTO = new ParamDTO();
        List<TermDTO> termDTOList = new ArrayList<>();
        for (Term term : termList) {
            TermDTO termDTO = new TermDTO();
            BeanUtils.copyProperties(term, termDTO);
            termDTOList.add(termDTO);
            paramDTO.setTermDTO(termDTOList);
        }

        List<Course> courseList = courseMapper.findAll();
        List<CourseDTO> courseDTOList = new ArrayList<>();
        for (Course course : courseList) {
            CourseDTO courseDTO = new CourseDTO();
            BeanUtils.copyProperties(course, courseDTO);
            courseDTOList.add(courseDTO);
            paramDTO.setCourseDTO(courseDTOList);
        }

        List<Class> class1List = classMapper.findAll();
        List<ClassDTO> classDTOList = class1List.stream().map(class1 -> {
            ClassDTO classDTO = new ClassDTO();
            BeanUtils.copyProperties(class1, classDTO);
            return classDTO;
        }).collect(Collectors.toList());
        paramDTO.setClassDTO(classDTOList);
        return paramDTO;
    }
}
