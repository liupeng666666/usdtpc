package com.whp.usdtpc.report.Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/11/22 15:05
 * @descrpition :
 */
public interface ReportSubTeamMoneyInterface {

    public JSONObject SubTeamMoneyInsert(Map<String, Object> map);

    public JSONObject SubTeamMoneySelect(Map<String, Object> map);
}
