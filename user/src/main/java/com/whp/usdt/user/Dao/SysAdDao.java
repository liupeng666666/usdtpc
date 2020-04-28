package com.whp.usdt.user.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysAdDao {
    /**
     * 根据位置检索广告信息
     *
     * @param location 广告位置类型
     * @return
     */
    List<Map<String, Object>> getAdByLocation(@Param("location") int location);
}
