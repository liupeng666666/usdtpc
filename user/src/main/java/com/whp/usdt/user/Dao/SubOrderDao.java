package com.whp.usdt.user.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SubOrderDao {
    /**
     * 根据时间段查询收益最大排行榜
     *
     * @param begintime 开始时间
     * @param endtime   结束时间
     * @return
     */
    List<Map<String, Object>> getProfitTop(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("userid") String userid, @Param("beginnumber") int beginnumber, @Param("endnumber") int endnumber);

    /**
     * 根据时间段查询收益最大排行榜条数
     *
     * @param begintime 开始时间
     * @param endtime   结束时间
     * @return
     */
    int getProfitTopCount(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("userid") String userid);

    /**
     * 获取最近的交易订单
     *
     * @return
     */
    List<Map<String, Object>> getSubOrderSortDesc(@Param("beginnumber") int beginnumber, @Param("endnumber") int endnumber);

    /**
     * 获取交易订单总条数
     *
     * @return
     */
    int getSubOrderSortDescCount();

    /**
     * 添加新的订单
     *
     * @param map
     */
    void addSubOrder(Map<String, Object> map);

    /**
     * 查询该用户下所有可跟随订单
     *
     * @param userid
     * @return
     */
    List<Map<String, Object>> getFollowOrder(@Param("userid") String userid, @Param("follower") String follower);

    /**
     * 根据用户id查询订单币种信息
     *
     * @param userid 用户id
     * @return
     */
    List<Map<String, Object>> getOrderByCurrency(@Param("userid") String userid);

    /**
     * 根据用户id分区查询订单信息
     *
     * @param userid
     * @param style
     * @param begintime
     * @param endtime
     * @param beginnumber
     * @param endnumber
     * @param state
     * @return
     */
    List<Map<String, Object>> getOrderByTradeSector(@Param("userid") String userid, @Param("style") int style, @Param("begintime") String begintime, @Param("endtime") String endtime,
                                                    @Param("beginnumber") int beginnumber, @Param("endnumber") int endnumber, @Param("state") String state);

    /**
     * 根据用户id分区查询订单信息
     *
     * @param userid
     * @param style
     * @param begintime
     * @param endtime
     * @param state
     * @return
     */
    int getOrderByTradeSectorCnt(@Param("userid") String userid, @Param("style") int style, @Param("begintime") String begintime, @Param("endtime") String endtime,
                                 @Param("state") String state);

    /**
     * 获取收益率统计
     *
     * @param userid    用户id
     * @param begintime 开始时间
     * @param endtime   结束时间
     * @return
     */
    List<Map<String, Object>> getRateOfReturnByTime(@Param("userid") String userid, @Param("begintime") String begintime, @Param("endtime") String endtime);

    /**
     * 获取收益金额统计
     *
     * @param userid
     * @param begintime
     * @param endtime
     * @return
     */
    List<Map<String, Object>> getIncomeAmountByTime(@Param("userid") String userid, @Param("begintime") String begintime, @Param("endtime") String endtime);

    /**
     * 获取净值余额统计
     *
     * @param userid
     * @param begintime
     * @param endtime
     * @return
     */
    List<Map<String, Object>> getNetValueBalanceById(@Param("userid") String userid, @Param("begintime") String begintime, @Param("endtime") String endtime);

    /**
     * 根据用户分页查询最近的下单记录
     *
     * @param userid      用户id
     * @param beginnumber 当前页码
     * @param endnumber   条数
     * @return
     */
    List<Map<String, Object>> getSubOrderByUserPage(@Param("userid") String userid, @Param("type") String type, @Param("beginnumber") int beginnumber, @Param("endnumber") int endnumber);

    /**
     * 根据用户分页查询最近的下单记录条数
     *
     * @param userid
     * @return
     */
    int getSubOrderByUserPageCount(@Param("userid") String userid, @Param("type") String type);
}
