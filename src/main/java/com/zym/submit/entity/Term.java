package com.zym.submit.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Term {

    private Integer termId;

    private String studyYear;

    private Byte termName;

    private Byte isValid;

    private Date createTime;

    private Date updateTime;

    private Integer gradeId;

}