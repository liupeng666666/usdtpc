package com.whp.usdtpc.business.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/1 10:25
 * @descrpition :
 */
@Mapper
public interface SubDiscDao {

    /**
     * @param map
     * @return
     */
    Map<String, Object> SysDiscSelect(Map<String, Object> map);

    /**
     * @param map
     * @return
     */
    Map<String, Object> SysDiscIsStart(Map<String, Object> map);

    /**
     * @param map
     * @return
     */
    Map<String, Object> SysDiscSelectPid(Map<String, Object> map);

}
