package com.whp.usdt.user.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Dao.SysAdDao;
import com.whp.usdt.user.Interface.SysAdInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysAdImpl implements SysAdInterface {
    @Autowired
    private SysAdDao sysAdDao;

    @Override
    public JSONObject getAdByLocation(int location) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> ad = sysAdDao.getAdByLocation(location);
            if (ad != null && ad.size() > 0) {
                json.put("code", 100);
                json.put("sysad", ad);
            } else {
                json.put("code", 101);
                json.put("message", "系统中未找到匹配的记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }
}
