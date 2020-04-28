package com.whp.usdt.user.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysClassDao {
    /**
     * 根据类型获取分类
     *
     * @param style 类型 0:新闻分类，1公告分类，2关于我们
     * @return
     */
    List<Map<String, Object>> getClassByStyle(@Param("style") String style);
}
