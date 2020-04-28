package com.whp.usdtpc.WebIM.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtpc.WebIM.Dao.FriendRequestDao;
import com.whp.usdtpc.WebIM.Dao.SubFriendDao;
import com.whp.usdtpc.WebIM.Interface.SubFriendInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/7/30 14:45
 * @descrpition :
 */
@Service
public class SubFriendImpl implements SubFriendInterface {
    @Autowired
    private SubFriendDao subFriendDao;
    @Autowired
    private FriendRequestDao friendRequestDao;

    @Override
    public JSONObject SubFriendSelect(String userid) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = subFriendDao.SubFriend(userid);
            json.put("code", 100);
            json.put("friend", list);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject SubFriendInsert(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            int num = friendRequestDao.getRelationCount(map.get("userid").toString(), map.get("sub_user_id").toString());
            if (num <= 0) {
                subFriendDao.SubFriendInsert(map);
            }
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject delFriend(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            if ("chat".equals(map.get("roomtype").toString())) {
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("userid", map.get("roomid"));
                map2.put("roomid", map.get("userid"));
                subFriendDao.delFriend(map2);
            } else {
                subFriendDao.groupNumDel(map.get("roomid").toString());
            }
            subFriendDao.delFriend(map);

            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject searchFriends(String name, String userid) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> user = subFriendDao.searchFriends(name, userid);
            List<Map<String, Object>> group = subFriendDao.searchGroup(name, userid);
            if (group != null) {
                if (user == null) {
                    user = new ArrayList<Map<String, Object>>();
                }
                for (int i = 0; i < group.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("pid", group.get(i).get("pid"));
                    map.put("img", group.get(i).get("img"));
                    map.put("nickname", group.get(i).get("name"));
                    map.put("type", "group");
                    user.add(map);
                }
            }
            json.put("friend", user);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject getFriendsByGroupId(String roomid, String userid) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> user = subFriendDao.getFriendsByGroupId(roomid, userid);
            if (user != null && user.size() > 0) {
                json.put("friend", user);
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
}
