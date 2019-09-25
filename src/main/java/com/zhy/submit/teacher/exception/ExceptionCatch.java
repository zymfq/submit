package com.zhy.submit.teacher.exception;

import com.zhy.submit.teacher.VO.ResultVO;
import com.zhy.submit.teacher.enums.ResultEnum;
import com.zhy.submit.teacher.utils.ResultVOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//统一异常捕获类，对捕获的异常进行处理

//使用了这个注解就只能捕获controller层的异常了
@ControllerAdvice
public class ExceptionCatch {
    private static final org.slf4j.Logger LOGGER=LoggerFactory.getLogger(ExceptionCatch.class);
    //处理可预知的异常
    @ExceptionHandler(SubmitException.class)
    @ResponseBody
    public ResultVO submitException(SubmitException submitException){
        LOGGER.error("catch exception",submitException.getMessage());
        return ResultVOUtils.error(submitException.getResultCode());
    }
}
