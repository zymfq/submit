package com.zhy.submit.teacher.enums;

public interface ResultCode {
   //操作是否成功，true为成功
    boolean success();
    //错误码
    int code();
    //错误信息
    String message();

}
