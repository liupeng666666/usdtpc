package com.whp.usdt.user.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface SysDiscDao {
    /**
     * 根据交易区和币种获取盘口信息
     *
     * @param style
     * @return
     */
    Map<String, Object> getDiscInfoByDist(@Param("style") String style, @Param("sys_currency_id") String sys_currency_id);
}
