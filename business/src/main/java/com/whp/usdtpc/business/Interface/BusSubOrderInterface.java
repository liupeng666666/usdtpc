package com.whp.usdtpc.business.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/1 23:21
 * @descrpition :
 */
public interface BusSubOrderInterface {

    /**
     * @param map
     * @return
     */
    JSONObject SubOrderUserSelect(Map<String, Object> map);

    /**
     * @param map
     * @return
     */
    JSONObject SubOrderAdd(Map<String, Object> map);

    /**
     * @param map
     * @return
     */
    JSONObject SubOrderUpdate(Map<String, Object> map);

    /**
     * @param pid
     * @return
     */
    JSONObject SubOrderDatetime(String pid);

    /**
     * @param userid
     * @return
     */
    JSONObject SubOrderBQUSelect(String userid);
}
