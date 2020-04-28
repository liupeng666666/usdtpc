package com.whp.usdt.user.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface SubUsdtInterface {
    /**
     * 获取钱包地址
     *
     * @return
     */
    JSONObject getSysWallet();

    /**
     * 添加一条新的USDT记录
     *
     * @param map
     * @return
     */
    JSONObject addSubUsdt(Map<String, Object> map);

    /**
     * 根据用户id和动作查询USDT记录
     *
     * @param userid
     * @param style
     * @return
     */
    JSONObject getSubUsdtByIdStyle(String userid, String style, int page, int number, String codeid);

    JSONObject getFreezeUsdt(String userid);
}
