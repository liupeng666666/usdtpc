package com.whp.usdt.user.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface SubOrderInterface {
    /**
     * 根据时间段查询收益最大排行榜
     *
     * @param begintime 开始时间
     * @param endtime   结束时间
     * @param userid    用户id
     * @return
     */
    JSONObject getProfitTop(String begintime, String endtime, String userid, int page, int number);

    /**
     * 获取最近交易订单
     *
     * @return
     */
    JSONObject getSubOrderSortDesc(int page, int number);

    /**
     * 添加新的订单
     *
     * @param map
     * @return
     */
    JSONObject addSubOrder(Map<String, Object> map);

    /**
     * 查询该用户下当前所有可跟随订单
     *
     * @param userid 用户id
     * @return
     */
    JSONObject getFollowOrder(String userid, String follower);

    /**
     * 根据用户id查询币种信息
     *
     * @param userid
     * @return
     */
    JSONObject getOrderByCurrency(String userid);

    /**
     * 根据交易区和用户id查询最近的订单
     *
     * @param userid
     * @param style
     * @param begintime
     * @param endtime
     * @param page
     * @param number
     * @param state
     * @return
     */
    JSONObject getOrderByTradeSector(String userid, int style, String begintime, String endtime, int page, int number, String state);

    /**
     * 获取收益率统计
     *
     * @param userid    用户id
     * @param begintime 开始时间
     * @param endtime   结束时间
     * @return
     */
    JSONObject getRateOfReturnByTime(String userid, String begintime, String endtime);

    /**
     * 获取收益金额统计
     *
     * @param userid    用户id
     * @param begintime 开始时间
     * @param endtime   结束时间
     * @return
     */
    JSONObject getIncomeAmountByTime(String userid, String begintime, String endtime);

    /**
     * 获取净值余额统计
     *
     * @param userid 用户id
     * @return
     */
    JSONObject getNetValueBalanceById(String userid, String begintime, String endtime);

    /**
     * 根据用户id分页查询订单信息
     *
     * @param userid
     * @param page
     * @param number
     * @return
     */
    JSONObject getSubOrderByUserPage(String userid, int page, int number, String style);
}
