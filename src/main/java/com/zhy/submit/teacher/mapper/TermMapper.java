package com.zhy.submit.teacher.mapper;

import com.zhy.submit.teacher.entity.Term;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TermMapper {
    int selectTermIdByForm(String schoolYear,String termName,String gradeName);
    int deleteByPrimaryKey(Integer termId);

    int insert(Term record);

    int insertSelective(Term record);

    Term selectByPrimaryKey(Integer termId);

    int updateByPrimaryKeySelective(Term record);

    int updateByPrimaryKey(Term record);
}