package com.whp.usdt.user.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SubUsdtDao {
    /**
     * 获取系统钱包地址
     *
     * @return
     */
    Map<String, Object> getSysWallet();

    /**
     * 添加一条新的USDT记录
     *
     * @param map
     */
    int addSubUsdt(Map<String, Object> map);

    int addSubUsdtPass(Map<String, Object> map);

    /**
     * 根据用户id和动作查询USDT记录
     *
     * @param userid
     * @param style
     * @param beginnumber
     * @param endnumber
     * @return
     */
    List<Map<String, Object>> getSubUsdtByIdStyle(@Param("userid") String userid, @Param("style") String style, @Param("beginnumber") int beginnumber, @Param("endnumber") int endnumber, @Param("codeid") String codeid);

    Map<String, Object> getFreezeUsdt(@Param("userid") String userid);
}
