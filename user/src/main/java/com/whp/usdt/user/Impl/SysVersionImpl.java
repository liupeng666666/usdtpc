package com.whp.usdt.user.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Dao.SysVersionDao;
import com.whp.usdt.user.Interface.SysVersionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/11/6 18:03
 * @descrpition :
 */
@Service
public class SysVersionImpl implements SysVersionInterface {

    @Autowired
    private SysVersionDao sysVersionDao;

    @Override
    public JSONObject SysVersion(int type) {
        JSONObject json = new JSONObject();
        try {
            Map<String, Object> map = sysVersionDao.getSysVersion(type);
            if (map == null) {
                json.put("code", 102);
            } else {
                JSONObject jsonObject = new JSONObject(map);
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("version", jsonObject.getString("version"));
                jsonObject1.put("savePath", jsonObject.getString("savepath"));

                if (type == 1) {
                    jsonObject.put("android", jsonObject1);
                } else {
                    jsonObject.put("ios", jsonObject1);
                }
                json.put("code", 100);
                json.put("sj", jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
