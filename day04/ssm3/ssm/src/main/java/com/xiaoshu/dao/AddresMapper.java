package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Addres;
import com.xiaoshu.entity.AddresExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AddresMapper extends BaseMapper<Addres> {
    long countByExample(AddresExample example);

    int deleteByExample(AddresExample example);

    List<Addres> selectByExample(AddresExample example);

    int updateByExampleSelective(@Param("record") Addres record, @Param("example") AddresExample example);

    int updateByExample(@Param("record") Addres record, @Param("example") AddresExample example);
}