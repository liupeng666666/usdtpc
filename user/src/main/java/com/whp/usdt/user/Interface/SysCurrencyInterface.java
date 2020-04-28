package com.whp.usdt.user.Interface;

import com.alibaba.fastjson.JSONObject;

public interface SysCurrencyInterface {
    /**
     * 获取所有的币种信息
     *
     * @return
     */
    public JSONObject getAllCurrency();
}
