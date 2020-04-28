package com.whp.usdtpc.WebIM.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.utils.JWTUtil;
import com.whp.usdtpc.WebIM.Interface.SubFriendInterface;
import com.whp.usdtpc.WebIM.Interface.SubGroupInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/7/30 14:52
 * @descrpition :
 */
@RestController
@RequestMapping("subfriend")
public class SubFriendController {
    @Autowired
    private SubFriendInterface subFriendInterface;
    @Autowired
    private SubGroupInterface subGroupInterface;

    @PostMapping("SubFriendSelect")
    public JSONObject SubFriendSelect(@RequestParam String userid) {
        JSONObject json = subFriendInterface.SubFriendSelect(userid);
        return json;
    }

    @PostMapping("SubFriendInsert")
    public JSONObject SubFriendInsert(@RequestParam Map<String, Object> map) {
        JSONObject json = subFriendInterface.SubFriendInsert(map);
        if (map.containsKey("state")) {
            if (Integer.parseInt(map.get("state").toString()) == 1) {
                subGroupInterface.SubGroupUpdate(map.get("userid").toString());
            }
        }
        return json;
    }

    /**
     * 删除好友或群关系
     *
     * @param map
     * @return
     */
    @PostMapping("delFriend")
    public JSONObject delFriend(@RequestParam Map<String, Object> map) {
        JSONObject json = subFriendInterface.delFriend(map);
        return json;
    }

    /**
     * 模糊检索用户
     *
     * @param name
     * @return
     */
    @PostMapping("searchFriends")
    public JSONObject searchFriends(String name, String userid) {
        JSONObject json = subFriendInterface.searchFriends(name, userid);
        return json;
    }

    /**
     * 根据查询该用户好友不在此群的用户
     *
     * @param roomid
     * @param userid
     * @return
     */
    @PostMapping("getFriendsByGroupId")
    public JSONObject getFriendsByGroupId(String roomid, String userid) {
        JSONObject json = subFriendInterface.getFriendsByGroupId(roomid, userid);
        return json;
    }
}
