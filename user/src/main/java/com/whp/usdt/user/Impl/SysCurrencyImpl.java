package com.whp.usdt.user.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Dao.SysCurrencyDao;
import com.whp.usdt.user.Interface.SysCurrencyInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysCurrencyImpl implements SysCurrencyInterface {
    @Autowired
    private SysCurrencyDao sysCurrencyDao;

    @Override
    public JSONObject getAllCurrency() {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = sysCurrencyDao.getAllCurrency();
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "数据库异常，请重新获取数据");
        }
        return json;
    }
}
