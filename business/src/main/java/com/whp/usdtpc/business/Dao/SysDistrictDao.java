package com.whp.usdtpc.business.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/19 23:06
 * @descrpition :
 */
@Mapper
public interface SysDistrictDao {
    /**
     * @param level
     * @return
     */
    List<Map<String, Object>> SysDistrictSelect(@Param("level") int level);
}
