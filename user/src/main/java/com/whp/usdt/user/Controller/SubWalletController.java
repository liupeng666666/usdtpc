package com.whp.usdt.user.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Interface.SubWalletInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 吴敬鹏
 * @date 2018-07-13
 * @Deprecated 平台用户操作类
 */
@RestController
@RequestMapping("/subwallet")
public class SubWalletController {
    @Autowired
    private SubWalletInterface subWalletInterface;

    /**
     * 获取用户充值地址
     *
     * @param userid
     * @return
     */
    @RequestMapping(value = "/getUserRechargeUrl", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getUserRechargeUrl(String userid) {
        JSONObject json = new JSONObject();
        json = subWalletInterface.getUserRechargeUrl(userid);
        return json;
    }

    /**
     * 获取用户提现参数
     *
     * @return
     */
    @RequestMapping(value = "/getSysWithdrawParam", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getSysWithdrawParam() {
        JSONObject json = new JSONObject();
        json = subWalletInterface.getSysWithdrawParam();
        return json;
    }
}
