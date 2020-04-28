package com.whp.usdt.user.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface SubMoneyDao {
    /**
     * 修改用户余额
     *
     * @param userid
     * @param surplus..
     * @return
     */
    int changeSurplus(@Param("userid") String userid, @Param("surplus") float surplus);

    /**
     * 修改用户余额和总金额
     *
     * @param userid
     * @param money
     */
    void changeSurplusAndTotal(@Param("userid") String userid, @Param("money") float money);

    void addMoney(Map<String, Object> map);

    Map<String, Object> getMoneyInfo(@Param("userid") String userid);

    int addSubDetailed(Map<String, Object> map);

    /**
     * 修改用户余额
     *
     * @param userid
     * @param surplus..
     * @return
     */
    int changeSurplusBru(@Param("userid") String userid, @Param("surplus") float surplus);
}
