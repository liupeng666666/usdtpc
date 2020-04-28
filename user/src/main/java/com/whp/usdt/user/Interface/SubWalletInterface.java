package com.whp.usdt.user.Interface;

import com.alibaba.fastjson.JSONObject;

public interface SubWalletInterface {
    /**
     * 获取用户提现地址
     *
     * @param userid
     * @return
     */
    JSONObject getUserRechargeUrl(String userid);

    /**
     * 获取用户提现参数
     *
     * @return
     */
    JSONObject getSysWithdrawParam();
}
