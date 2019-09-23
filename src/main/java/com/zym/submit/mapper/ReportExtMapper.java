package com.zym.submit.mapper;

import com.zym.submit.dto.ReportDTO;
import com.zym.submit.entity.TaskNotice;

import java.util.List;

/**
 * @author zym
 * @date 2019-09-09-12:50
 */
public interface ReportExtMapper {

    List<ReportDTO> selectByCourseId(String studentNumber,Integer termId, Integer courseId);

    List<TaskNotice> selectNotSubmit(String studentNumber,Integer termId, Integer courseId);

    List<TaskNotice> selectAllNotSubmit(String studentNumber,Integer termId,Integer classId);

    List<ReportDTO> selectReportByStudentNumber(String studentNumber, Integer page, Integer size);

}
