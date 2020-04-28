package com.whp.usdtpc.business.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/2 2:23
 * @descrpition :
 */
@Mapper
public interface BusSubMoneyDao {

    /**
     * @param map
     * @return
     */
    Map<String, Object> SubMoneySelect(Map<String, Object> map);

    /**
     * @param map
     */
    void SubMoneyUpdate(Map<String, Object> map);

    List<Map<String, Object>> SubTeamSelect(@Param("userid") String userid);

    void SubMoneyTeamUpdate(Map<String, Object> map);
}
