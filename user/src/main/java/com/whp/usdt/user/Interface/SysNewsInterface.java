package com.whp.usdt.user.Interface;

import com.alibaba.fastjson.JSONObject;

public interface SysNewsInterface {
    /**
     * 根据分类查询新闻
     *
     * @param sys_class_id 分类id
     * @return
     */
    JSONObject getSysNewsByClass(String sys_class_id, int page, int number);

    /**
     * 查询24小时快讯
     *
     * @param page
     * @param number
     * @return
     */
    JSONObject getSysNewsFlash(int page, int number);

    /**
     * 查询单个新闻
     *
     * @param pid
     * @return
     */
    JSONObject getNewsMessage(String pid);
}
