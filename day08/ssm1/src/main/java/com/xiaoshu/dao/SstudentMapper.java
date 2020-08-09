package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Sstudent;
import com.xiaoshu.entity.SstudentExample;
import com.xiaoshu.entity.SstudentVo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SstudentMapper extends BaseMapper<Sstudent> {
    long countByExample(SstudentExample example);

    int deleteByExample(SstudentExample example);

    List<Sstudent> selectByExample(SstudentExample example);

    int updateByExampleSelective(@Param("record") Sstudent record, @Param("example") SstudentExample example);

    int updateByExample(@Param("record") Sstudent record, @Param("example") SstudentExample example);

	List<SstudentVo> findPage(SstudentVo sstudentVo);
}