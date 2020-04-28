package com.whp.usdtpc.WebIM.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/7/30 14:36
 * @descrpition :
 */
@Mapper
public interface SubGroupDao {

    /**
     * @param map
     * @return
     */
    List<Map<String, Object>> SubGroup(Map<String, Object> map);

    /**
     * @param map
     * @return
     */
    List<Map<String, Object>> SubGroupTop(Map<String, Object> map);

    /**
     * @param pid
     */
    void SubGroupUpdate(@Param("pid") String pid);

    List<Map<String, Object>> getGroupByUser(Map<String, Object> map);

    List<Map<String, Object>> getGroupUserInfo(Map<String, Object> map);

    int addGroupInfo(Map<String, Object> map);

    int addFriendRelation(Map<String, Object> map);

    void inviteFriends(@Param("uuid") String uuid, @Param("roomid") String roomid, @Param("sub_user_id") String sub_user_id);

    Map<String, Object> getGroupById(@Param("groupid") String groupid);

    int changeGroupInfo(Map<String, Object> map);

    int groupNumAdd(@Param("pid") String pid);
}

