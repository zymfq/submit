package com.zym.submit.dto;

import lombok.Data;

/**
 * @author zym
 * @date 2019-09-08-14:34
 */
@Data
public class StudentDTO {

    private Integer studentId;

    private String studentNumber;

    private String studentName;

    private String studentPassword;

    private Byte studentSex;
}
