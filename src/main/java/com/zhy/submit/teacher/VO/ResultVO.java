package com.zhy.submit.teacher.VO;

import lombok.Data;
@Data
public class ResultVO<T> {
        //操作是否成功
        private boolean success;
        //返回编码
        private int code;
        //提示信息
        private String msg;
        //具体内容
        private T data;


}
