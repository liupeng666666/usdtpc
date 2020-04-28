package com.whp.usdtpc.WebIM.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Dao.SubMoneyDao;
import com.whp.usdt.user.Dao.SubUserDao;
import com.whp.usdt.user.utils.MD5Util;
import com.whp.usdtpc.WebIM.Dao.FriendRequestDao;
import com.whp.usdtpc.WebIM.Dao.SubFriendDao;
import com.whp.usdtpc.WebIM.Dao.SubGroupDao;
import com.whp.usdtpc.WebIM.Interface.FriendRequestInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FriendRequestImpl implements FriendRequestInterface {
    @Autowired
    private FriendRequestDao friendRequestDao;
    @Autowired
    private SubMoneyDao subMoneyDao;
    @Autowired
    private SubUserDao subUserDao;
    @Autowired
    private SubFriendDao subFriendDao;
    @Autowired
    private SubGroupDao subGroupDao;

    @Override
    public JSONObject addFriendRequest(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {

            if (map.containsKey("type") && "1".equals(map.get("type").toString())) {
                float paymoney = 0 - Float.parseFloat(map.get("paymoney").toString());
                int num = subMoneyDao.changeSurplus(map.get("proposer").toString(), paymoney);
                Map<String, Object> proposer = subUserDao.getUserInfoById(map.get("proposer").toString());
                Map<String, Object> psdmap = new HashMap<String, Object>();
                String pid1 = MD5Util.createId();
                psdmap.put("pid", pid1);
                psdmap.put("surplus", proposer.get("surplus"));
                psdmap.put("loss", map.get("paymoney"));
                psdmap.put("state", "7");
                psdmap.put("userid", map.get("proposer"));
                int num2 = subMoneyDao.addSubDetailed(psdmap);

                int num4 = subMoneyDao.changeSurplus(map.get("owner").toString(), Float.parseFloat(map.get("paymoney").toString()));
                Map<String, Object> umap = subUserDao.getUserInfoById(map.get("owner").toString());
                Map<String, Object> usdmap = new HashMap<String, Object>();
                String pid2 = MD5Util.createId();
                usdmap.put("pid", pid2);
                usdmap.put("surplus", umap.get("surplus"));
                usdmap.put("income", map.get("paymoney"));
                usdmap.put("state", "9");
                usdmap.put("userid", map.get("owner"));
                usdmap.put("pay_userid", map.get("proposer"));
                int num5 = subMoneyDao.addSubDetailed(usdmap);


                Date date = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);//设置起时间
                cal.add(cal.YEAR, 1);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                Map<String, Object> sfmap = new HashMap<String, Object>();
                sfmap.put("userid", map.get("userid"));
                sfmap.put("sub_user_id", map.get("proposer"));
                sfmap.put("expire", format.format(cal.getTime()));
                subFriendDao.SubFriendInsert(sfmap);
                subGroupDao.groupNumAdd(map.get("userid").toString());

            } else {
                String pid = MD5Util.createId();
                map.put("pid", pid);
                int num = friendRequestDao.addFriendRequest(map);
            }
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject getUntreatedFriendRequest(String userid) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = friendRequestDao.getUntreatedFriendRequest(userid);
            if (list != null && list.size() > 0) {
                json.put("code", 100);
                json.put("fr", list);
            } else {
                json.put("code", 101);
            }
        } catch (Exception e) {
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject changeFriendRequestState(String pid, String userid) {
        JSONObject json = new JSONObject();
        try {
            int num = friendRequestDao.changeFriendRequestState(pid, userid);
            json.put("code", 100);
        } catch (Exception e) {
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject addOpenChatRoom(String roomid, String userid) {
        JSONObject json = new JSONObject();
        try {
            int num = friendRequestDao.getRelationCount(roomid, userid);
            if (num <= 0) {
                int num2 = friendRequestDao.addSubFriend(roomid, userid, null);
                int num3 = subGroupDao.groupNumAdd(roomid);
            }
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
