package com.whp.usdt.user.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Dao.SysNewsDao;
import com.whp.usdt.user.Interface.SysNewsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysNewsImpl implements SysNewsInterface {
    @Autowired
    private SysNewsDao sysNewsDao;

    @Override
    public JSONObject getSysNewsByClass(String sys_class_id, int page, int number) {
        JSONObject json = new JSONObject();
        try {
            int beginnumber = (page - 1) * number;
            List<Map<String, Object>> list = sysNewsDao.getSysNewsByClass(sys_class_id, beginnumber, number);
            if (list != null && list.size() > 0) {
                int total = sysNewsDao.getSysNewsByClassCount(sys_class_id);
                json.put("code", 100);
                json.put("news", list);
                json.put("total", total);
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

    @Override
    public JSONObject getSysNewsFlash(int page, int number) {
        JSONObject json = new JSONObject();
        try {
            int beginnumber = (page - 1) * number;
            List<Map<String, Object>> list = sysNewsDao.getSysNewsFlash(beginnumber, number);
            if (list != null && list.size() > 0) {
                int total = sysNewsDao.getSysNewsFlashCount();
                json.put("code", 100);
                json.put("news", list);
                json.put("total", total);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject getNewsMessage(String pid) {
        JSONObject json = new JSONObject();
        try {
            Map<String, Object> map = sysNewsDao.getNewsMessage(pid);
            sysNewsDao.getNewsUpdate(pid);
            json.put("code", 100);
            json.put("news", map);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
