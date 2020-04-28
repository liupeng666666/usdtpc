package com.whp.usdtpc.business.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/1 21:38
 * @descrpition :
 */
@Mapper
public interface SysRiseFallDao {

    /**
     * @param map
     * @return
     */
    List<Map<String, Object>> SysRiseFallSelect(Map<String, Object> map);
}
