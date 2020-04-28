package com.whp.usdt.user.Interface;

import com.alibaba.fastjson.JSONObject;

public interface SysClassInterface {
    /**
     * 根据类型获取分类
     *
     * @param style 类型 0:新闻分类，1公告分类，2关于我们
     * @return
     */
    JSONObject getClassByStyle(String style);
}
