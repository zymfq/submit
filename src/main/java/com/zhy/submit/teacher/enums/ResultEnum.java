package com.zhy.submit.teacher.enums;

import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;
@Getter
public enum ResultEnum {
    para_error(0,"参数错误"),
    account_not_exit(1,"账号不存在"),
    format_error(2,"上传文件格式错误"),
    unselected(3,"未选择上传文件"),
    file_size_error(4,"上传文件过大"),






    ;
    private Integer code;
    private String msg;
    ResultEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }

}
