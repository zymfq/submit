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
    CLASS_NOT_EXIST(1003,"班级信息不存在"),
    COURSE_NOT_EXIST(1004,"课程信息不存在"),
    TERM_NOT_EXIST(1005,"学期信息不存在"),

    //200开头代表学生相关信息错误
    STUDENT_LOGIN_FAIL(2001,"学生账号或密码不正确"),
    STUDENT_NOT_LOGIN(2002,"用户未登录~"),
    STUDENT_NOT_EXIST(2003,"用户不存在"),

    //300开头表示实验报告信相关信息错误
    FORMAT_ERROR(3001,"上传文件格式错误！"),
    FILE_SIZE_ERROR(3002,"上传文件内存过大！" ),
    FILE_NOT_FIND(3003,"未选择文件！"),
    FILE_IS_MISS(3004,"莫非文件不翼而飞？"),
    ALL_REPORT_IS_SUBMIT(3005,"暂无未提交试验报告"),
    REPEATED_SUBMIT(3006,"请不要重复提交"),
    DOWNLOAD_FAIL(3007,"实验报告下载失败"),

    //400开头表示管理员信息不存在
    ADMIN_MESSAGE_NOT_FIND(4001,"用户信息不存在"),
    USER_MESSAGE_NOT_FIND(4000,"用户账户或密码错误")
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
