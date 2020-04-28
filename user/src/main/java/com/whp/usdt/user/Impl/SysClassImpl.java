package com.whp.usdt.user.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Dao.SysClassDao;
import com.whp.usdt.user.Interface.SysClassInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysClassImpl implements SysClassInterface {
    @Autowired
    private SysClassDao sysClassDao;

    @Override
    public JSONObject getClassByStyle(String style) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = sysClassDao.getClassByStyle(style);
            if (list != null && list.size() > 0) {
                json.put("code", 100);
                json.put("sysclass", list);
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
