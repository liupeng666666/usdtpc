package com.whp.usdtpc.WebIM.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface FriendRequestInterface {

    JSONObject addFriendRequest(Map<String, Object> map);

    JSONObject getUntreatedFriendRequest(String userid);

    JSONObject changeFriendRequestState(String pid, String userid);

    JSONObject addOpenChatRoom(String roomid, String userid);
}
