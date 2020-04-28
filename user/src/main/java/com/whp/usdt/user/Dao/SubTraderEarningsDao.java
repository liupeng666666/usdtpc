package com.whp.usdt.user.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SubTraderEarningsDao {
    /**
     * 根据周期查询交易员收益排行榜
     *
     * @param begintime   开始时间
     * @param endtime     结束时间
     * @param beginnumber 当前页码
     * @param endnumber   条数
     * @return
     */
    List<Map<String, Object>> getSubTraderEarningsTop(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("beginnumber") int beginnumber, @Param("endnumber") int endnumber);

    /**
     * 根据周期查询交易员收益排行榜
     *
     * @param begintime   开始时间
     * @param endtime     结束时间
     * @param beginnumber 当前页码
     * @param endnumber   条数
     * @return
     */
    List<Map<String, Object>> getSubTraderFollowerEarnings(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("beginnumber") int beginnumber, @Param("endnumber") int endnumber, @Param("userid") String userid);

    /**
     * 根据周期查询交易员收益排行榜总条数
     *
     * @param begintime 开始时间
     * @param endtime   结束时间
     * @return
     */
    int getSubTraderFollowerEarningsCount(@Param("begintime") String begintime, @Param("endtime") String endtime);

    /**
     * 根据类型查询用户交易员收入
     *
     * @param userid
     * @param type
     * @return
     */
    Map<String, Object> getTraderEarningsByType(@Param("userid") String userid, @Param("type") int type);

    /**
     * 根据用户id和类型获取用户交易记录
     *
     * @param userid
     * @param type
     * @param beginnumber
     * @param endnumber
     * @return
     */
    List<Map<String, Object>> getUserPayRecordByType(@Param("pay_userid") String pay_userid, @Param("userid") String userid, @Param("type") String type, @Param("beginnumber") int beginnumber, @Param("endnumber") int endnumber);

    /**
     * 根据用户id和类型获取用户交易记录条数
     *
     * @param userid
     * @param type
     * @return
     */
    int getUserPayRecordByTypeCount(@Param("pay_userid") String pay_userid, @Param("userid") String userid, @Param("type") String type);

    /**
     * 根据交易类型查询交易员收入
     *
     * @param userid
     * @param type
     * @param beginnumber
     * @param endnumber
     * @return
     */
    List<Map<String, Object>> getTradeEarningsByUserIdType(@Param("userid") String userid, @Param("type") int type, @Param("beginnumber") int beginnumber, @Param("endnumber") int endnumber);

    /**
     * 根据交易类型查询交易员收入条数
     *
     * @param userid
     * @param type
     * @return
     */
    int getTradeEarningsByUserIdTypeCount(@Param("userid") String userid, @Param("type") int type);

    /**
     * 添加新的交易员收益
     *
     * @param pid
     * @param userid
     * @param money
     * @param type
     * @param orderid
     * @return
     */
    int addTradeEarnings(@Param("pid") String pid, @Param("userid") String userid, @Param("money") float money, @Param("type") int type, @Param("orderid") String orderid, @Param("pay_userid") String pay_userid);

    /**
     * @param userid
     * @return
     */
    List<Map<String, Object>> SubDetailedShou(@Param("userid") String userid, @Param("page") int page, @Param("num") int num);

    int SubDetailedShouCount(@Param("userid") String userid);
}
