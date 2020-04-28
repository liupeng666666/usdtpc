package com.whp.usdtpc.report.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtpc.report.Dao.ReportSubExtractDao;
import com.whp.usdtpc.report.Interface.ReportSubExtractInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/11/20 16:48
 * @descrpition :
 */
@Service
public class ReportSubExtractImpl implements ReportSubExtractInterface {
    @Autowired
    private ReportSubExtractDao reportSubExtractDao;

    @Override
    public JSONObject SubExtractSelect(String pid, int page, int num) {
        JSONObject json = new JSONObject();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pid", pid);
            jsonObject.put("page", (page - 1) * num);
            jsonObject.put("num", num);
            List<Map<String, Object>> list = reportSubExtractDao.SubExtractSelect(jsonObject);
            int count = reportSubExtractDao.SubExtractCount(jsonObject);
            json.put("code", 100);
            json.put("extract", list);
            json.put("total", count);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
