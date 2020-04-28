package com.whp.usdtpc.WebIM.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FriendRequestDao {

    int addFriendRequest(Map<String, Object> map);

    List<Map<String, Object>> getUntreatedFriendRequest(@Param("userid") String userid);

    int changeFriendRequestState(@Param("pid") String pid, @Param("userid") String userid);

    int getRelationCount(@Param("roomid") String roomid, @Param("userid") String userid);

    int addSubFriend(@Param("userid") String userid, @Param("sub_user_id") String sub_user_id, @Param("expire") String expire);
}
