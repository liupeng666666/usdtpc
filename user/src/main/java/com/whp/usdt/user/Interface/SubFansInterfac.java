package com.whp.usdt.user.Interface;

import com.alibaba.fastjson.JSONObject;

public interface SubFansInterfac {
    /**
     * 添加新的粉丝
     *
     * @param sub_user_id 要关注者id
     * @param userid      用户id
     * @return
     */
    JSONObject addFans(String sub_user_id, String userid);

    /**
     * 根据用户id查询粉丝
     *
     * @param userid
     * @return
     */
    JSONObject getFansByUserid(String userid);

    /**
     * 根据用户id查询用户的关注
     *
     * @param userid
     * @return
     */
    JSONObject getConcernByUserid(String userid);

    /**
     * 用户取消关注
     *
     * @param sub_user_id
     * @param userid
     * @return
     */
    JSONObject delFans(String sub_user_id, String userid);

    /**
     * 根据用户id查询用户关注的交易员
     *
     * @param userid
     * @return
     */
    JSONObject getFollowerTraderById(String userid);
}
