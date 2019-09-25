package com.zhy.submit.teacher.controller;

import com.zhy.submit.teacher.VO.ResultVO;
import com.zhy.submit.teacher.converter.ConverterAddform2AddDTO;
import com.zhy.submit.teacher.dto.addReportDTO;
import com.zhy.submit.teacher.dto.showReportDTO;
import com.zhy.submit.teacher.enums.ResultEnum;
import com.zhy.submit.teacher.exception.SubmitException;
import com.zhy.submit.teacher.form.addReportForm;
import com.zhy.submit.teacher.service.addReportService;
import com.zhy.submit.teacher.utils.ResultVOUtils;
import com.zhy.submit.teacher.utils.UUIDUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/addReport")
public class addReportController {
    @Autowired
   addReportService addReportService;
    //添加实验报告
   @PostMapping("/add")
    public ModelAndView addReport(@Valid addReportForm addForm,
                                  BindingResult bindingResult,
                                  HttpServletRequest request,
                                  HttpServletResponse response){
       if (bindingResult.hasErrors())
           throw new SubmitException(ResultEnum.para_error);
       //转换得到DTO
       addReportDTO addDTO= ConverterAddform2AddDTO.convert(addForm);
       //上传文件,并将URL放入DTO
       if(addDTO.getFile()!=null){
           Map<String,Object> map=addReportService.upload(addDTO.getFile(),request,response);
           addDTO.setTaskPath((String) map.get("path"));
       }
      //文件名的UUID
       addDTO.setTaskId(UUIDUtils.getUUID());
       //获取term_id
       Integer in=addReportService.getTeamId(addDTO);
       addDTO.setTermId(in);
       //获取classId
       Integer integer= addReportService.getclassID(addDTO.getSchoolClass());
       addDTO.setClassId(integer);
       //获取courseId
        Integer inte=addReportService.getCourseId(addDTO.getCourse());
        addDTO.setCourseId(inte);
        //添加
        addReportService.add(addDTO);
       return new ModelAndView("success");
   }
   //显示所有添加的实验报告
    @GetMapping("/showAddReport")
    @ResponseBody
    public ResultVO show(@RequestParam("teacherNumber") String teacherNumber,@RequestParam("currPage") int currPage,@RequestParam("pageSize") int pageSize){
        List<showReportDTO> showReportDTOList=addReportService.view(teacherNumber,currPage,pageSize);
        ResultVO resultVO= ResultVOUtils.success(showReportDTOList);
        return  resultVO;
    }
    //编辑已发布的实验报告信息

    //删除发布的实验报告信息，根据老师工号和实验名称
    @GetMapping("/cancel")
    @ResponseBody
    public ResultVO cancel(@RequestParam("teacherNumber") String teacherNumber,@RequestParam("experimentName") String experimentName){
       Integer integer;
        try {
           integer=addReportService.cancelReport(teacherNumber, experimentName);
        } catch (Exception e) {
           throw new SubmitException(ResultEnum.delete_fail);
        }

        return ResultVOUtils.success(integer);
    }

}