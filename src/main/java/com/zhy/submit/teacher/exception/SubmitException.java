package com.zhy.submit.teacher.exception;

import com.zhy.submit.teacher.enums.ResultCode;


public class SubmitException extends RuntimeException{
    private ResultCode code;
    public SubmitException(ResultCode resultCode){
        super("错误代码："+resultCode.code()+"错误信息："+resultCode.message());
        this.code=resultCode;

    }
    public ResultCode getResultCode(){
        return this.code;
    }


}
