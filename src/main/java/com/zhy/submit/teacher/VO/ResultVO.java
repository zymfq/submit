package com.zhy.submit.teacher.VO;

import lombok.Data;
@Data
public class ResultVO<T> {

        //返回编码
        private int code;
        //提示信息
        private String msg;
        //数据总数
        private int count;
        //具体内容
        private T data;


}
