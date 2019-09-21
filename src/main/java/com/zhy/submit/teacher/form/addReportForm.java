package com.zhy.submit.teacher.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;


@Data
public class addReportForm {
    @NotEmpty(message = "学年不能为空")
    private String schoolYear;

    @NotEmpty(message = "学期不能为空")
    private String term;

    @NotEmpty(message = "年级不能为空")
    private String grade;

    @NotEmpty(message = "班级不能为空")
    private String schoolClass;

    @NotEmpty(message = "课程名不能为空")
    private String course;

    //老师工号
    private String teacherNumber;



    @NotEmpty(message = "实验名称不能为空")
    private String experimentName;

    //@NotEmpty(message = "截止日期不能为空")
    private Date submitDeadline;

    private MultipartFile file;
}
