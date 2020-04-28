package com.whp.usdtpc.WebIM.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Dao.SubMoneyDao;
import com.whp.usdt.user.Dao.SubUserDao;
import com.whp.usdt.user.utils.MD5Util;
import com.whp.usdtpc.WebIM.Dao.SubGroupDao;
import com.whp.usdtpc.WebIM.Interface.SubGroupInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : 张吉伟
 * @data : 2018/7/30 14:48
 * @descrpition :
 */
@Service
public class SubGroupImpl implements SubGroupInterface {
    @Autowired
    private SubGroupDao subGroupDao;
    @Autowired
    private SubMoneyDao subMoneyDao;
    @Autowired
    private SubUserDao subUserDao;

    @Override
    public JSONObject SubGroupTop(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = subGroupDao.SubGroupTop(map);
            json.put("group", list);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject SubGroupUpdate(String pid) {
        JSONObject json = new JSONObject();
        try {
            subGroupDao.SubGroupUpdate(pid);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject getGroupByUser(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = subGroupDao.getGroupByUser(map);
            json.put("group", list);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject getGroupUserInfo(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = subGroupDao.getGroupUserInfo(map);
            if (list != null && list.size() > 0) {
                json.put("group", list);
                json.put("code", 100);
            } else {
                json.put("code", 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject addGroupInfo(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);//设置起时间
            cal.add(cal.YEAR, 1);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            map.put("expire", format.format(cal.getTime()));
            int num = subGroupDao.addGroupInfo(map);
            String uuid = MD5Util.createId();
            map.put("uuid", uuid);
            int num2 = subGroupDao.addFriendRelation(map);
            float paymoney = -365;
            int num3 = subMoneyDao.changeSurplus(map.get("userid").toString(), paymoney);
            Map<String, Object> proposer = subUserDao.getUserInfoById(map.get("userid").toString());
            Map<String, Object> psdmap = new HashMap<String, Object>();
            String pid1 = MD5Util.createId();
            psdmap.put("pid", pid1);
            psdmap.put("surplus", proposer.get("surplus"));
            psdmap.put("loss", 365);
            psdmap.put("state", "7");
            psdmap.put("userid", map.get("userid"));
            int num4 = subMoneyDao.addSubDetailed(psdmap);
//            int num5 = subGroupDao.groupNumAdd(map.get("pid").toString());
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject inviteFriends(String roomid, String userid, String friends) {
        JSONObject json = new JSONObject();
        try {
            String[] friend = friends.split(",");
            for (int i = 0; i < friend.length; i++) {
                String uuid = MD5Util.createId();
                subGroupDao.inviteFriends(uuid, roomid, friend[i]);
                subGroupDao.groupNumAdd(roomid);
            }
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject getGroupById(String groupid) {
        JSONObject json = new JSONObject();
        try {
            Map<String, Object> map = subGroupDao.getGroupById(groupid);
            if (map != null) {
                json.put("group", map);
                json.put("code", 100);
            } else {
                json.put("code", 101);
            }

        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject changeGroupInfo(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            if (map.containsKey("groupid") && map.get("groupid") != null) {
                int num = subGroupDao.changeGroupInfo(map);
                json.put("code", 100);
            } else {
                json.put("code", 102);
                json.put("message", "请求参数错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject SubGroupSelect(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = subGroupDao.SubGroup(map);
            json.put("group", list);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
