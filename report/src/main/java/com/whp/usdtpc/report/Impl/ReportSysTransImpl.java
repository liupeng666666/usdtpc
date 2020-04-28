package com.whp.usdtpc.report.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtpc.report.Dao.ReportSysTransDao;
import com.whp.usdtpc.report.Interface.ReportSysTransInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/11/23 9:32
 * @descrpition :
 */
@Service
public class ReportSysTransImpl implements ReportSysTransInterface {
    @Autowired
    private ReportSysTransDao sysTransDao;

    @Override
    public JSONObject ReportSysTrans() {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = sysTransDao.SysTrans();
            json.put("trans", list);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }

        return json;
    }
}
