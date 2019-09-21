package com.zhy.submit.teacher.exception;

import com.zhy.submit.teacher.enums.ResultEnum;

public class SubmitException extends RuntimeException{
    private Integer code;
    public SubmitException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code=resultEnum.getCode();

    }
    public SubmitException(Integer code,String message){
        super(message);
        this.code=code;
    }

}
