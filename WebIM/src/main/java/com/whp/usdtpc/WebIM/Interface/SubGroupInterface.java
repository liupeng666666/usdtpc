package com.whp.usdtpc.WebIM.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/7/30 14:43
 * @descrpition :
 */
public interface SubGroupInterface {

    JSONObject SubGroupSelect(Map<String, Object> map);

    /**
     * @param map
     * @return
     */
    JSONObject SubGroupTop(Map<String, Object> map);

    /**
     * @param pid
     * @return
     */
    JSONObject SubGroupUpdate(String pid);

    JSONObject getGroupByUser(Map<String, Object> map);

    JSONObject getGroupUserInfo(Map<String, Object> map);

    JSONObject addGroupInfo(Map<String, Object> map);

    /**
     * 邀请好友入群
     *
     * @param roomid
     * @param userid
     * @param friends
     * @return
     */
    JSONObject inviteFriends(String roomid, String userid, String friends);

    /**
     * 根据群组id查询群信息
     *
     * @param groupid
     * @return
     */
    JSONObject getGroupById(String groupid);

    JSONObject changeGroupInfo(Map<String, Object> map);
}
