package com.whp.usdt.user.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Dao.SubFansDao;
import com.whp.usdt.user.Dao.SubUserDao;
import com.whp.usdt.user.Interface.SubFansInterfac;
import com.whp.usdt.user.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SubFansImpl implements SubFansInterfac {
    @Autowired
    private SubFansDao subFansDao;
    @Autowired
    private SubUserDao subUserDao;

    @Override
    public JSONObject addFans(String sub_user_id, String userid) {
        JSONObject json = new JSONObject();
        try {
            if (sub_user_id != null && !"".equals(sub_user_id) && userid != null && !"".equals(userid)) {
                String pid = MD5Util.createId();
                subFansDao.addFans(sub_user_id, userid, pid);
                subUserDao.addFollowNumber(sub_user_id);
                json.put("code", 100);
            } else {
                json.put("code", 102);
                json.put("message", "请求语法错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject delFans(String sub_user_id, String userid) {
        JSONObject json = new JSONObject();
        try {
            if (sub_user_id != null && !"".equals(sub_user_id) && userid != null && !"".equals(userid)) {
                subFansDao.delFans(sub_user_id, userid);
                subUserDao.delFollowNumber(sub_user_id);
                json.put("code", 100);
            } else {
                json.put("code", 102);
                json.put("message", "请求语法错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject getFollowerTraderById(String userid) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = subFansDao.getFollowerTraderById(userid);
            if (list != null && list.size() > 0) {
                json.put("code", 100);
                json.put("concern", list);
            } else {
                json.put("code", 101);
                json.put("message", "未找到匹配的记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject getFansByUserid(String userid) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = subFansDao.getFansByUserid(userid);
            if (list != null && list.size() > 0) {
                json.put("code", 100);
                json.put("fans", list);
            } else {
                json.put("code", 101);
                json.put("message", "未找到匹配的记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject getConcernByUserid(String userid) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = subFansDao.getConcernByUserid(userid);
            if (list != null && list.size() > 0) {
                json.put("code", 100);
                json.put("concern", list);
            } else {
                json.put("code", 101);
                json.put("message", "未找到匹配的记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }
}
