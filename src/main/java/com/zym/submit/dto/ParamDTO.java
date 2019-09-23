package com.zym.submit.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zym
 * @date 2019-09-23-17:07
 */
@Data
public class ParamDTO {

    private List<TermDTO> termDTO;

    private List<CourseDTO> courseDTO;

    private List<ClassDTO> classDTO;

}
