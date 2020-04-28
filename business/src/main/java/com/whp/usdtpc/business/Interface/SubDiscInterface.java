package com.whp.usdtpc.business.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/1 10:26
 * @descrpition :
 */
public interface SubDiscInterface {

    /**
     * @param map
     * @return
     */
    JSONObject SysDiscSelect(Map<String, Object> map);

    /**
     * @param map
     * @return
     */
    JSONObject SysDiscIsStart(Map<String, Object> map, String pid);
}
