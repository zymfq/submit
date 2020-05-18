package com.zym.submit.enums;

import lombok.Getter;

/**
 * 用于一次性查询所有的实验报告 其中展示未提交 和 提交的信息
 * 实验报告状态
 * @author zym
 * @date 2019-09-26-19:07
 */
@Getter
public enum ReportStatusEnum {

    SUBMITTED(1,"已提交"),
    NOT_SUBMITTED(0,"未提交");

    private Integer type;
    private String status;

    ReportStatusEnum(Integer type, String status) {
        this.type = type;
        this.status = status;
    }

}
