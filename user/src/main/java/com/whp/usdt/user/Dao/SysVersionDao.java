package com.whp.usdt.user.Dao;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/11/6 18:01
 * @descrpition :
 */
public interface SysVersionDao {

    /**
     * @param type
     * @return
     */
    Map<String, Object> getSysVersion(@Param("type") int type);
}
