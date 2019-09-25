package com.zhy.submit.teacher.enums;

import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;
@Getter
public enum ResultEnum implements ResultCode  {
    //以多少开头的是什么错误先定义好
    para_error(false,20001,""),
    account_error(false,20002,"账号或者密码错误"),
    format_error(false,20003,"上传文件格式错误"),
    unselected(false,2004,"未选择上传文件"),
    file_size_error(false,2005,"上传文件过大"),
    number_isnull(false,2006,"  请输入工号"),
    password_isnull(false,2007,"请输入密码"),
    delete_fail(false,2008,"删除实验报告失败"),
    score_format_error(false,2009,"请输入1-100之间的正确分数"),
    mark_fail(false,2010,"打分失败"),
    download_fail(false,2011,"下载失败"),
    show_error(false,2012,"无法显示正确内容"),
    ;
    private boolean success;
    private int code;
    private String msg;
    ResultEnum(boolean success,int code,String msg){
        this.success=success;
        this.code=code;
        this.msg=msg;
    }


    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return msg;
    }
}
