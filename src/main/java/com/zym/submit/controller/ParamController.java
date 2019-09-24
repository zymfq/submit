package com.zym.submit.controller;

import com.zym.submit.dto.ParamDTO;
import com.zym.submit.response.CommonReturnType;
import com.zym.submit.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 参数接口
 * @author zym
 * @date 2019-09-23-17:09
 */
@Controller
public class ParamController {

    @Autowired
    private ParamService paramService;

    /**
     * 获取参数  List<TermDTO>termDTO;
     *          List<CourseDTO> courseDTO;
     *          List<ClassDTO> classDTO;
     * @return
     */
    @GetMapping("/param")
    @ResponseBody
    public CommonReturnType selectParam(){

        ParamDTO paramDTO = paramService.findParam();
        return CommonReturnType.okOf(paramDTO);
    }

}
