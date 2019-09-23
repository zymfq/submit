package com.zym.submit.enums;

import lombok.Getter;

/**
 * @author zym
 * @date 2019-09-23-14:12
 */
@Getter
public enum TermTypeEnum {
    FIRST_TERM(1, "第一学期"),
    SECOND_TERM(2, "第二学期");

    private int type;
    private String name;

    TermTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String nameOfType(int type){
        for(TermTypeEnum termTypeEnum : TermTypeEnum.values()){
            if(termTypeEnum.getType() == type){
                return termTypeEnum.getName();
            }
        }
        return "";
    }
}

