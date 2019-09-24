package com.zym.submit.service;

import com.zym.submit.dto.ReportDTO;
import com.zym.submit.dto.TaskDTO;
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

    /**
     * 查询所有试验报告
     *
     * @param studentNumber
     * @return
     */
    List<ReportDTO> listReport(String studentNumber,Integer pageNum, Integer pageSize);

    /**
     * 根据学期课程查询试验报告
     *
     * @param studentNumber
     * @param termId
     * @param courseId
     * @return
     */
    List<ReportDTO> listReportByCourseId(String studentNumber, Integer termId, Integer courseId,Integer pageNum, Integer pageSize);


    /**
     * 查询所有未提交的实验报告
     *
     * @param studentNumber
     * @return
     */
    List<TaskDTO> listAllNotSubmit(String studentNumber, Integer termId, Integer classId,Integer pageNum, Integer pageSize);

    /**
     * 根据学期课程查询未提交的实验报告
     *
     * @param studentNumber
     * @param termId
     * @param courseId
     * @return
     */
    List<TaskDTO> listNotSubmit(String studentNumber, Integer termId, Integer courseId);


    /**
     * 撤回实验报告
     * @param taskId
     * @return
     */
    int rollBackReport(Integer taskId);

}