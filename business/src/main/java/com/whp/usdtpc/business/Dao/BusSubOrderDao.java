package com.whp.usdtpc.business.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/1 10:33
 * @descrpition :
 */
@Mapper
public interface BusSubOrderDao {

    /**
     * @param sys_disc_id
     * @return
     */
    Map<String, Object> SubOrderSelect(@Param("sys_disc_id") String sys_disc_id);

    /**
     * @param map
     * @return
     */
    List<Map<String, Object>> SubOrderUserSelect(Map<String, Object> map);

    /**
     * @param map
     */
    void SubOrderAdd(Map<String, Object> map);

    /**
     * @param map
     */
    void SubOrderUpdate(Map<String, Object> map);

    /**
     * @param pid
     * @return
     */
    List<Map<String, Object>> SubOrderDatetime(@Param("pid") String pid);

    /**
     * @param userid
     * @return
     */
    List<Map<String, Object>> SubOrderBQUSelect(@Param("userid") String userid);
}
