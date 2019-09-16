package com.zym.submit.mapper;

import com.zym.submit.entity.TaskNotice;

/**
 * @author zym
 * @date 2019-09-16-21:04
 */
public interface TaskNoticeExtMapper {

    TaskNotice selectByTaskId(Integer taskId);

}
