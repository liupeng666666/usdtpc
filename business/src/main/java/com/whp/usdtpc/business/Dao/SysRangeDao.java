package com.whp.usdtpc.business.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/1 21:38
 * @descrpition :
 */
@Mapper
public interface SysRangeDao {

    /**
     * @param map
     * @return
     */
    List<Map<String, Object>> SysRangeSelect(Map<String, Object> map);

    /**
     * @param pid
     * @return
     */
    Map<String, Object> SysRangeDan(@Param("pid") String pid);
}
