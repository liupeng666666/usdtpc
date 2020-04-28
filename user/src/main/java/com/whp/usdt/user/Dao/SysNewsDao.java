package com.whp.usdt.user.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysNewsDao {
    /**
     * 根据分类查询新闻
     *
     * @param sys_class_id 分类id
     * @return
     */
    List<Map<String, Object>> getSysNewsByClass(@Param("sys_class_id") String sys_class_id, @Param("beginnumber") int beginnumber, @Param("endnumber") int endnumber);

    /**
     * 根据分类查询新闻共多少条
     *
     * @param sys_class_id
     * @return
     */
    int getSysNewsByClassCount(@Param("sys_class_id") String sys_class_id);

    /**
     * 查询24小时快讯
     *
     * @param beginnumber
     * @param endnumber
     * @return
     */
    List<Map<String, Object>> getSysNewsFlash(@Param("beginnumber") int beginnumber, @Param("endnumber") int endnumber);

    /**
     * 查询24小时快讯共多少条数据
     *
     * @return
     */
    int getSysNewsFlashCount();

    /**
     * @param pid
     * @return
     */
    Map<String, Object> getNewsMessage(@Param("pid") String pid);

    /**
     * @param pid
     */
    void getNewsUpdate(@Param("pid") String pid);

}
