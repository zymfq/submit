package com.zym.submit.service;

import com.zym.submit.dto.ReportDTO;
import com.zym.submit.dto.TaskDTO;
import com.zym.submit.entity.Report;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author zym
 * @date 2019-09-08-20:43
 */
public interface ReportService {

    Map<String, Object> upload(MultipartFile myFiles, Integer taskId, String studentNumber,
                               HttpServletRequest request, HttpServletResponse response);

    List<ReportDTO> listReport(String studentNumber);

    List<Report> listReportByCourseId(String studentNumber, Integer termId, Integer courseId);

    List<TaskDTO> listNotSubmit(String studentNumber, Integer termId, Integer courseId);

}
