package com.zym.submit.mapper;

import com.zym.submit.entity.TaskNotice;
import com.zym.submit.entity.entityExample.TaskNoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskNoticeMapper {
    long countByExample(TaskNoticeExample example);

    int deleteByExample(TaskNoticeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TaskNotice record);

    int insertSelective(TaskNotice record);

    List<TaskNotice> selectByExample(TaskNoticeExample example);

    TaskNotice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TaskNotice record, @Param("example") TaskNoticeExample example);

    int updateByExample(@Param("record") TaskNotice record, @Param("example") TaskNoticeExample example);

    int updateByPrimaryKeySelective(TaskNotice record);

    int updateByPrimaryKey(TaskNotice record);
}