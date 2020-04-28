package com.whp.usdtpc.business.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/1 21:40
 * @descrpition :
 */
public interface SysRangeInterface {

    /**
     * @param map
     * @return
     */
    JSONObject SysRangeSelect(Map<String, Object> map);
}
