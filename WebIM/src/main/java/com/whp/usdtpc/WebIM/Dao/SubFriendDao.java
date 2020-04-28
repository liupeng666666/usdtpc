package com.whp.usdtpc.WebIM.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/7/30 14:35
 * @descrpition :
 */
@Mapper
public interface SubFriendDao {

    /**
     * @param userid
     * @return
     */
    List<Map<String, Object>> SubFriend(@Param("userid") String userid);

    /**
     * @param map
     */
    void SubFriendInsert(Map<String, Object> map);

    void delFriend(Map<String, Object> map);

    List<Map<String, Object>> searchFriends(@Param("name") String name, @Param("userid") String userid);

    List<Map<String, Object>> searchGroup(@Param("name") String name, @Param("userid") String userid);

    List<Map<String, Object>> getFriendsByGroupId(@Param("roomid") String roomid, @Param("userid") String userid);

    void groupNumDel(@Param("roomid") String roomid);
}
