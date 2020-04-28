package com.whp.usdtpc.report.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtpc.report.Dao.ReportSubMoneyDao;
import com.whp.usdtpc.report.Interface.ReportSubMoneyInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/11/20 15:02
 * @descrpition :
 */
@Service
public class ReportSubMoneyImpl implements ReportSubMoneyInterface {
    @Autowired
    private ReportSubMoneyDao reportSubMoneyDao;

    @Override
    public JSONObject SubMoneySelect(String pid) {
        JSONObject json = new JSONObject();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pid", pid);
            Map<String, Object> map = reportSubMoneyDao.SubMoneySelect(jsonObject);
            json.put("money", map);
            json.put("code", 100);
        } catch (Exception e) {
            json.put("code", 103);
            e.printStackTrace();
        }
        return json;
    }
}
