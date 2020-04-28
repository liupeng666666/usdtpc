package com.whp.usdt.user.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Dao.SubMoneyDao;
import com.whp.usdt.user.Interface.SubMoneyInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubMoneyImpl implements SubMoneyInterface {
    @Autowired
    private SubMoneyDao subMoneyDao;

    @Override
    public JSONObject changeSurplus(String userid, float surplus) {
        JSONObject json = new JSONObject();
        try {
            if (userid != null && !"".equals(userid)) {
                subMoneyDao.changeSurplus(userid, surplus);
                json.put("code", 100);
            } else {
                json.put("code", 101);
                json.put("message", "操作异常，请重新操作");

            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "数据库连接失败请重新操作");
        }
        return json;
    }
}
