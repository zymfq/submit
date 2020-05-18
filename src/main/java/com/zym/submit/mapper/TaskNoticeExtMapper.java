package com.zym.submit.mapper;

import com.zym.submit.dto.DownloadReportDTO;
import com.zym.submit.entity.TaskNotice;

/**
 * @author zym
 * @date 2020-05-17-23:37
 */
public interface TaskNoticeExtMapper {

    TaskNotice selectByTaskId(Integer taskId);

    //获取下载压缩文件的名字
    DownloadReportDTO getZipName(Integer taskId);
}
