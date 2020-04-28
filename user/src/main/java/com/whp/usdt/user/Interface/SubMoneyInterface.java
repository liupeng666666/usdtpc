package com.whp.usdt.user.Interface;

import com.alibaba.fastjson.JSONObject;

public interface SubMoneyInterface {
    /**
     * 修改用户钱包余额
     *
     * @param userid  用户id
     * @param surplus 要修改的值
     * @return
     */
    public JSONObject changeSurplus(String userid, float surplus);
}
