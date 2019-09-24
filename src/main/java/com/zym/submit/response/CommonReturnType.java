package com.zym.submit.response;

import lombok.Data;

/**
 * @author zym
 * @date 2019-09-08-15:55
 */
@Data
public class CommonReturnType<T> {

    private Integer code;

    private String message;

    private Integer count=1000;

    private T data;

    public static CommonReturnType errorOf(Integer code, String message){
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setCode(code);
        commonReturnType.setMessage(message);
        return commonReturnType;
    }

    public static CommonReturnType okOf(){
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setCode(200);
        commonReturnType.setMessage("请求成功！！！");
        return commonReturnType;
    }


    public static <T>CommonReturnType okOf(T t){
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setCode(200);
        commonReturnType.setMessage("请求成功！！！");
        commonReturnType.setData(t);
        return commonReturnType;
    }
}
