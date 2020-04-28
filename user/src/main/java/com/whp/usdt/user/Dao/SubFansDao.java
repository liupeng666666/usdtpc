package com.whp.usdt.user.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SubFansDao {
    /**
     * 添加新的粉丝
     *
     * @param sub_user_id 要关注者id
     * @param userid      用户id
     * @return
     */
    void addFans(@Param("sub_user_id") String sub_user_id, @Param("userid") String userid, @Param("pid") String pid);

    /**
     * 根据用户id查询粉丝
     *
     * @param userid 用户id
     * @return
     */
    List<Map<String, Object>> getFansByUserid(@Param("userid") String userid);

    /**
     * 根据用户id查询用户关注
     *
     * @param userid
     * @return
     */
    List<Map<String, Object>> getConcernByUserid(@Param("userid") String userid);

    /**
     * 用户取消关注
     *
     * @param sub_user_id
     * @param userid
     */
    void delFans(@Param("sub_user_id") String sub_user_id, @Param("userid") String userid);

    /**
     * 根据用户id查询用户关注的交易员
     *
     * @param userid
     * @return
     */
    List<Map<String, Object>> getFollowerTraderById(@Param("userid") String userid);
}
