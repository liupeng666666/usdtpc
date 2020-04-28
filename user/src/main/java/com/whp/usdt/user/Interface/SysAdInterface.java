package com.whp.usdt.user.Interface;

import com.alibaba.fastjson.JSONObject;

public interface SysAdInterface {
    /**
     * 根据位置检索广告信息
     *
     * @param location
     * @return
     */
    JSONObject getAdByLocation(int location);
}
