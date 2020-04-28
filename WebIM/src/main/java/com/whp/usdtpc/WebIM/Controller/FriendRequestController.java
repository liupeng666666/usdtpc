package com.whp.usdtpc.WebIM.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtpc.WebIM.Interface.FriendRequestInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("friendrequest")
public class FriendRequestController {
    @Autowired
    private FriendRequestInterface friendRequestInterface;

    @PostMapping("addFriendRequest")
    public JSONObject addFriendRequest(@RequestParam Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json = friendRequestInterface.addFriendRequest(map);
        return json;
    }

    @PostMapping("getUntreatedFriendRequest")
    public JSONObject getUntreatedFriendRequest(String userid) {
        JSONObject json = new JSONObject();
        json = friendRequestInterface.getUntreatedFriendRequest(userid);
        return json;
    }

    @PostMapping("changeFriendRequestState")
    public JSONObject changeFriendRequestState(String pid, String userid) {
        JSONObject json = new JSONObject();
        json = friendRequestInterface.changeFriendRequestState(pid, userid);
        return json;
    }

    @PostMapping("addOpenChatRoom")
    public JSONObject addOpenChatRoom(String roomid, String userid) {
        JSONObject json = new JSONObject();
        json = friendRequestInterface.addOpenChatRoom(roomid, userid);
        return json;
    }
}
