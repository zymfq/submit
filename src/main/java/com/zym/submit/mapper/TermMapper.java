package com.zym.submit.mapper;

import com.zym.submit.entity.Term;
import com.zym.submit.entity.entityExample.TermExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TermMapper {
    long countByExample(TermExample example);

    int deleteByExample(TermExample example);

    int deleteByPrimaryKey(Integer termId);

    int insert(Term record);

    int insertSelective(Term record);

    List<Term> selectByExample(TermExample example);

    Term selectByPrimaryKey(Integer termId);

    int updateByExampleSelective(@Param("record") Term record, @Param("example") TermExample example);

    int updateByExample(@Param("record") Term record, @Param("example") TermExample example);

    int updateByPrimaryKeySelective(Term record);

    int updateByPrimaryKey(Term record);

    List<Term> findAll();
}