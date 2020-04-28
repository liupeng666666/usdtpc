package com.whp.usdt.user.Interface;

import com.alibaba.fastjson.JSONObject;

public interface SysDiscInterface {
    /**
     * 根据交易区查询盘口信息
     *
     * @param style
     * @return
     */
    JSONObject getDiscInfoByDist(String style);
}
