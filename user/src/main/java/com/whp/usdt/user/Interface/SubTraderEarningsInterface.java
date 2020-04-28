package com.whp.usdt.user.Interface;

import com.alibaba.fastjson.JSONObject;

public interface SubTraderEarningsInterface {
    /**
     * 根据周期查询交易员收益排行榜
     *
     * @param begintime 开始时间
     * @param endtime   结束时间
     * @param page      当前页码
     * @param number    条数
     * @return
     */
    JSONObject getSubTraderEarningsTop(String begintime, String endtime, int page, int number);

    /**
     * 根据时间周期分页查询跟随者收益
     *
     * @param begintime 开始时间
     * @param endtime   结束时间
     * @param page      当前页码
     * @param number    查询条数
     * @return
     */
    JSONObject getSubTraderFollowerEarnings(String begintime, String endtime, int page, int number, String userid);

    /**
     * 根据类型查询交易员收入
     *
     * @param userid
     * @return
     */
    JSONObject getTraderEarningsByType(String userid);

    /**
     * 根据用户id和类型获取用户交易记录
     *
     * @param userid
     * @param type   类型：1：下单收入，2：好友奖励，3：查看交易员，4：充值，5：体现，6：赠送，
     * @return
     */
    JSONObject getUserPayRecordByType(String pay_userid, String userid, String type, int page, int number);

    /**
     * 根据交易类型查询交易员收入
     *
     * @param userid
     * @param type
     * @return
     */
    JSONObject getTradeEarningsByUserIdType(String userid, int type, int page, int number);

    /**
     * 添加新的交易员收益
     *
     * @param userid
     * @param orderid
     * @param follower
     * @param type
     * @param money
     * @return
     */
    JSONObject addTradeEarnings(String userid, String orderid, String follower, int type, float money);

    /**
     * @param userid
     * @return
     */
    JSONObject SubDetailedShou(String userid, int page, int num);
}
