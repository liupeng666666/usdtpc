package com.whp.usdtpc.report.Interface;

import com.alibaba.fastjson.JSONObject;

/**
 * @author : 张吉伟
 * @data : 2018/11/20 16:48
 * @descrpition :
 */
public interface ReportSubExtractInterface {
    public JSONObject SubExtractSelect(String pid, int page, int num);
}

