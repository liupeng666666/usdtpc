package com.whp.usdtpc.WebIM.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtpc.WebIM.Interface.SubGroupInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/7/30 14:50
 * @descrpition :
 */
@RestController
@RequestMapping("subgroup")
public class SubGroupController {
    @Autowired
    private SubGroupInterface subGroupInterface;

    @PostMapping("SubGroupSelect")
    public JSONObject SubGroupSelect(@RequestParam Map<String, Object> map) {
        JSONObject json = subGroupInterface.SubGroupSelect(map);
        return json;
    }

    @PostMapping("SubGroupTop")
    public JSONObject SubGroupTop(@RequestParam Map<String, Object> map) {
        JSONObject json = subGroupInterface.SubGroupTop(map);
        return json;
    }

    /**
     * 根据用户信息获取该用户群组信息
     *
     * @param map
     * @return
     */
    @PostMapping("getGroupByUser")
    public JSONObject getGroupByUser(@RequestParam Map<String, Object> map) {
        JSONObject json = subGroupInterface.getGroupByUser(map);
        return json;
    }

    /**
     * 获取该群组下所有成员信息
     *
     * @param map
     * @return
     */
    @PostMapping("getGroupUserInfo")
    public JSONObject getGroupUserInfo(@RequestParam Map<String, Object> map) {
        JSONObject json = subGroupInterface.getGroupUserInfo(map);
        return json;
    }

    /**
     * 添加群组信息
     *
     * @param map
     * @return
     */
    @PostMapping("addGroupInfo")
    public JSONObject addGroupInfo(@RequestParam Map<String, Object> map) {
        JSONObject json = subGroupInterface.addGroupInfo(map);
        return json;

    }

    /**
     * 邀请好友入群
     *
     * @return
     */
    @PostMapping("inviteFriends")
    public JSONObject inviteFriends(String roomid, String userid, String friends) {
        JSONObject json = subGroupInterface.inviteFriends(roomid, userid, friends);
        return json;
    }

    /**
     * 根据群组id查询群信息
     *
     * @param groupid
     * @return
     */
    @PostMapping("getGroupById")
    public JSONObject getGroupById(String groupid) {
        JSONObject json = new JSONObject();
        json = subGroupInterface.getGroupById(groupid);
        return json;
    }

    /**
     * 修改群组信息
     *
     * @param map
     * @return
     */
    @PostMapping("changeGroupInfo")
    public JSONObject changeGroupInfo(@RequestParam Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json = subGroupInterface.changeGroupInfo(map);
        return json;
    }

}
