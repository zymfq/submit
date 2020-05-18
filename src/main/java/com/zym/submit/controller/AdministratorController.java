package com.zym.submit.controller;

import com.zym.submit.dto.AdministratorDTO;
import com.zym.submit.response.CommonReturnType;
import com.zym.submit.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zym
 * @date 2019-11-14-20:12
 */
@Controller
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @PostMapping("/admin_login")
    @ResponseBody
    public CommonReturnType adminLogin(@RequestParam("teacherNumber") String teacherNumber,
                                       @RequestParam("teacherPassword") String teacherPassword){

        AdministratorDTO administratorDTO = administratorService.login(teacherNumber, teacherPassword);
        return CommonReturnType.okOf(administratorDTO);
    }
}
