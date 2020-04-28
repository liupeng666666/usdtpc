package com.whp.usdtpc.report.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtpc.report.Dao.ReportSubTeamDao;
import com.whp.usdtpc.report.Interface.ReportSubTeamInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/11/20 16:32
 * @descrpition :
 */
@Service
public class ReportSubTeamImpl implements ReportSubTeamInterface {

    @Autowired
    private ReportSubTeamDao subTeamDao;

    @Override
    public JSONObject SubTeam(String pid, int page, int num) {
        JSONObject json = new JSONObject();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pid", pid);
            jsonObject.put("page", (page - 1) * num);
            jsonObject.put("num", num);
            List<Map<String, Object>> list = subTeamDao.SubTeamSelect(jsonObject);
            int count = subTeamDao.SubTeamCount(jsonObject);
            json.put("code", 100);
            json.put("team", list);
            json.put("total", count);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }

        return json;
    }
}
