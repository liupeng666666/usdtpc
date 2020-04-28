package com.whp.usdt.user.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface SubWalletDao {
    /**
     * 查询用户充值地址
     *
     * @param userid
     * @return
     */
    Map<String, Object> getUserRechargeUrl(@Param("userid") String userid);

    /**
     * 添加用户充值地址
     *
     * @param pid
     * @param userid
     * @param wallet
     * @return
     */
    int addUserWalletUrl(@Param("pid") String pid, @Param("userid") String userid, @Param("wallet") String wallet);

    /**
     * 获取提现参数
     *
     * @return
     */
    Map<String, Object> getSysWithdrawParam();

    int addSubDetailed(Map<String, Object> map);
}
