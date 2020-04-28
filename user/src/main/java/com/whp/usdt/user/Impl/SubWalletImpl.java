package com.whp.usdt.user.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Dao.SubWalletDao;
import com.whp.usdt.user.Interface.SubWalletInterface;
import com.whp.usdt.user.utils.HttpClientUtil;
import com.whp.usdt.user.utils.MD5Util;
import com.whp.usdt.user.utils.Redis;
import com.whp.usdt.user.utils.SimpleHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SubWalletImpl implements SubWalletInterface {
    @Autowired
    private SubWalletDao subWalletDao;

    @Override
    public JSONObject getUserRechargeUrl(String userid) {
        JSONObject json = new JSONObject();
        try {
            String nr = Redis.get_yzm("WALLET.3", 7);
            if ("1".equals(nr)) {
                json.put("code", 171);
                return json;
            }
            Map<String, Object> map = subWalletDao.getUserRechargeUrl(userid);
            if (map == null) {
                String pid = MD5Util.createId();
                Map<String, String> param = new HashMap<String, String>();
                param.put("pid", userid);
                String wallet = HttpClientUtil.doPost("https://gl.b-currency.com/usdt/omni/OmniNewAddress", "UTF-8", param);
                JSONObject j = JSONObject.parseObject(wallet);
                subWalletDao.addUserWalletUrl(pid, userid, j.getString("wallet"));
                json.put("code", 100);
                json.put("wallet", j.getString("wallet"));
            } else {
                json.put("code", 100);
                json.put("wallet", map.get("wallet"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject getSysWithdrawParam() {
        JSONObject json = new JSONObject();
        try {
            String nr = Redis.get_yzm("WALLET.3", 7);
            if ("1".equals(nr)) {
                json.put("code", 171);
                return json;
            }
            Map<String, Object> map = subWalletDao.getSysWithdrawParam();
            if (map != null) {
                json.put("code", 100);
                json.put("param", map);
            } else {
                json.put("code", 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
