package com.zym.submit.exception;

import com.zym.submit.response.CommonReturnType;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author zym
 * @Date 2019-07-22-16:00
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonReturnType doError(Exception ex) {

        ex.printStackTrace();   //这里可以让他显示错误的异常信息，便于开发

        if (ex instanceof SubmitException) {
            SubmitException submitException = (SubmitException) ex;
            return CommonReturnType.errorOf(submitException.getCode(), submitException.getMessage());

        } else if (ex instanceof ServletRequestBindingException) {
            return CommonReturnType.errorOf(SubmitErrorCode.UNKNOWN_ERROR.getCode(), "url绑定路由问题");

        } else if (ex instanceof NoHandlerFoundException) {
            return CommonReturnType.errorOf(SubmitErrorCode.UNKNOWN_ERROR.getCode(), "没有找到对应的访问路径");

        } else {
            return CommonReturnType.errorOf(SubmitErrorCode.UNKNOWN_ERROR.getCode(),
                    SubmitErrorCode.UNKNOWN_ERROR.getMessage());
        }
    }
}
