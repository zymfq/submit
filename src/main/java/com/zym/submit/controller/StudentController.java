package com.zym.submit.controller;

import com.zym.submit.dto.StudentDTO;
import com.zym.submit.exception.SubmitErrorCode;
import com.zym.submit.exception.SubmitException;
import com.zym.submit.response.CommonReturnType;
import com.zym.submit.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zym
 * @date 2019-09-08-14:31
 */
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;


    @PostMapping("/login")
    public CommonReturnType login(@RequestParam(name = "studentNumber") String studentNumber,
                                  @RequestParam(name = "studentPassword") String studentPassword,
                                  HttpServletRequest request){

        //校验登录参数是否合法
        if(StringUtils.isEmpty(studentNumber) || StringUtils.isEmpty(studentPassword)){
            throw new SubmitException(SubmitErrorCode.PARAMETER_VALIDATION_ERROR);
        }
        //学生登录
        StudentDTO studentDTO = studentService.login(studentNumber, studentPassword);

        request.getSession().setAttribute("name", "studentInfo");

        return CommonReturnType.okOf(studentDTO);
    }


    @GetMapping("/logout")
    public CommonReturnType logout(HttpServletRequest request){
        request.getSession().invalidate();
        return CommonReturnType.okOf();
    }


    @PostMapping("/update")
    public CommonReturnType updatePassword(@RequestParam(name = "studentNumber") String studentNumber,
                                           @RequestParam(name = "studentPassword") String studentPassword){
        StudentDTO studentDTO = studentService.updatePassword(studentNumber, studentPassword);

        return CommonReturnType.okOf(studentDTO);
    }

}
