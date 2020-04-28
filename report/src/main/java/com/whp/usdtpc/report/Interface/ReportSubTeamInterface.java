package com.whp.usdtpc.report.Interface;

import com.alibaba.fastjson.JSONObject;

/**
 * @author : 张吉伟
 * @data : 2018/11/20 16:29
 * @descrpition :
 */
public interface ReportSubTeamInterface {

    public JSONObject SubTeam(String pid, int page, int num);
}
