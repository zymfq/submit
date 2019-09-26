package com.zhy.submit.teacher.controller;

import com.zhy.submit.teacher.VO.ResultVO;
import com.zhy.submit.teacher.dto.TeacherDTO;
import com.zhy.submit.teacher.enums.ResultEnum;
import com.zhy.submit.teacher.exception.SubmitException;
import com.zhy.submit.teacher.service.addReportService;
import com.zhy.submit.teacher.utils.ResultVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/teacher")
public class LoginController {
    @Autowired
    addReportService addReportService;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    @RequestMapping("/login")
    public String login(){

        return "login";
    }
    //教师登录
    @PostMapping("/loginCheck")
    @ResponseBody
    public ResultVO login(@RequestParam("teacherNumber") String number, @RequestParam("password") String password, HttpServletRequest request){
        if (number.equals(""))
            throw new SubmitException(ResultEnum.number_isnull);
        if(password.equals(""))
            throw new SubmitException(ResultEnum.password_isnull);
       TeacherDTO teacherDTO=new TeacherDTO();
       teacherDTO.setTeacherNumber(number);
       teacherDTO.setTeacherPassword(password);
       //将teacherNumber放进session
        HttpSession session=request.getSession();
        session.setAttribute("teacherNumber",number);
        TeacherDTO teacherDTO1= null;
        try {

            teacherDTO1 = addReportService.DoLogin(teacherDTO);
        } catch (Exception e) {
           throw new SubmitException(ResultEnum.account_error);
        }

        List<Object> list=new ArrayList<>();
        list.add(teacherDTO1.getTeacherNumber());
        list.add(teacherDTO1.getTeacherName());
        System.out.println(teacherDTO1.getTeacherName());
        return ResultVOUtils.success(1,list);

    }
}
