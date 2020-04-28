package com.whp.usdtpc.business.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/1 18:08
 * @descrpition :
 */
@Mapper
public interface SysSystemDao {
    /**
     * @return
     */
    Map<String, Object> SysSystemSelect();
}
