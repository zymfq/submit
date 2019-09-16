package com.zym.submit.exception;

/**
 * @author zym
 * @date 2019-09-08-14:43
 */
public class SubmitException extends RuntimeException {

    private Integer code;

    private String message;

    public SubmitException(ISubmitErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }


    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
