package com.zhy.submit.teacher.converter;

import com.zhy.submit.teacher.dto.addReportDTO;
import com.zhy.submit.teacher.form.addReportForm;

public class ConverterAddform2AddDTO {
    public static addReportDTO convert(addReportForm addFrom){
        addReportDTO addDTO=new addReportDTO();
        addDTO.setSchoolYear(addFrom.getSchoolYear());
        addDTO.setTerm(addFrom.getTerm());
        addDTO.setGrade(addFrom.getGrade());
        addDTO.setSchoolClass(addFrom.getSchoolClass());
        addDTO.setCourse(addFrom.getCourse());
        addDTO.setExperimentName(addFrom.getExperimentName());;
        addDTO.setSubmitDeadline(addFrom.getSubmitDeadline());
        addDTO.setFile(addFrom.getFile());
        addDTO.setTeacherNumber(addFrom.getTeacherNumber());
        return addDTO;
    }
}
