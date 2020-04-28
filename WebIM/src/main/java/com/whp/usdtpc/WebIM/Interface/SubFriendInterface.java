package com.whp.usdtpc.WebIM.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/7/30 14:44
 * @descrpition :
 */
public interface SubFriendInterface {

    /**
     * @param userid
     * @return
     */
    public JSONObject SubFriendSelect(String userid);

    /**
     * @param map
     * @return
     */
    JSONObject SubFriendInsert(Map<String, Object> map);

    JSONObject delFriend(Map<String, Object> map);

    /**
     * 模糊检索好友信息
     *
     * @param name
     * @return
     */
    JSONObject searchFriends(String name, String userid);

    JSONObject getFriendsByGroupId(String roomid, String userid);
}
