package com.whp.usdtpc.business.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/7/31 15:57
 * @descrpition :
 */
public interface SubUserCurrencyInterface {

    JSONObject SubUserCurrencySelect(Map<String, Object> map);

    JSONObject SubUserCurrencyInsert(Map<String, Object> map);
}
