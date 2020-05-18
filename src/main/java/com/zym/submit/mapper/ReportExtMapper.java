package com.zym.submit.mapper;

import com.zym.submit.entity.TaskNotice;

import java.util.List;

/**
 * @author zym
 * @date 2019-09-09-12:50
 */
public interface ReportExtMapper {

    List<TaskNotice> selectAllNotSubmit(String studentNumber,Integer termId,Integer classId);

}
