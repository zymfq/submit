package com.zym.submit.exception;

/**
 * @author zym
 * @date 2019-09-08-14:49
 */
public enum SubmitErrorCode implements ISubmitErrorCode {

    //通用错误类型
    UNKNOWN_ERROR(1000,"未知错误！！"),
    PARAMETER_VALIDATION_ERROR(1001, "参数不合法！"),
    SUBMIT_TIME_OVER(1002,"超出操作时间范围"),

    //200开头代表学生相关信息错误
    STUDENT_LOGIN_FAIL(2001,"学生账号或密码不正确"),
    STUDENT_NOT_LOGIN(2002,"用户未登录~"),

    //300开头表示实验报告信相关信息错误
    FORMAT_ERROR(3001,"上传文件格式错误！"),
    FILE_SIZE_ERROR(3002,"上传文件内存过大！" ),
    FILE_NOT_FIND(3003,"未选择文件！"),
    FILE_IS_MISS(3004,"莫非文件不翼而飞？"),
    ALL_REPORT_IS_SUBMIT(3005,"暂无未提交试验报告")
    ;

    private Integer code;

    private String message;

    SubmitErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
