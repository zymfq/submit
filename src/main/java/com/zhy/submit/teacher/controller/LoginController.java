package com.zhy.submit.teacher.controller;

import com.zhy.submit.teacher.dto.TeacherDTO;
import com.zhy.submit.teacher.enums.ResultEnum;
import com.zhy.submit.teacher.exception.SubmitException;
import com.zhy.submit.teacher.service.addReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/teacher")
public class LoginController {
    @Autowired
    addReportService addReportService;
    @RequestMapping("/index")
    public String index(){

        return "login";
    }
    //教师登录
    @PostMapping("/login")
    public ModelAndView login(@RequestParam("teacherNumber") String number,@RequestParam("password") String password, Map<String,Object> map){
       TeacherDTO teacherDTO=new TeacherDTO();
       teacherDTO.setTeacherNumber(number);
       teacherDTO.setTeacherPassword(password);

        TeacherDTO teacherDTO1= null;
        try {

            teacherDTO1 = addReportService.DoLogin(teacherDTO);
        } catch (Exception e) {
            throw new SubmitException(ResultEnum.para_error);
        }
        map.put("教师工号",teacherDTO1.getTeacherNumber());
        map.put("教师姓名",teacherDTO1.getTeacherName());
        System.out.println(teacherDTO1.getTeacherName());
        return new ModelAndView("add",map);

    }
}
