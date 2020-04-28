package com.whp.usdtpc.report.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtpc.report.Dao.ReportSubMoneyDao;
import com.whp.usdtpc.report.Dao.ReportSubTeamMoneyDao;
import com.whp.usdtpc.report.Dao.ReportSysTransDao;
import com.whp.usdtpc.report.Interface.ReportSubMoneyInterface;
import com.whp.usdtpc.report.Interface.ReportSubTeamMoneyInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/11/22 15:05
 * @descrpition :
 */
@Service
public class ReportSubTeamMoneyImpl implements ReportSubTeamMoneyInterface {

    @Autowired
    private ReportSubMoneyDao reportSubMoneyDao;
    @Autowired
    private ReportSubTeamMoneyDao subTeamMoneyDao;
    @Autowired
    private ReportSysTransDao reportSysTransDao;

    @Override
    public JSONObject SubTeamMoneyInsert(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            JSONObject jsonMap = new JSONObject(map);
            Map<String, Object> maps = reportSubMoneyDao.SubMoneyTeam(map);
            if (maps != null) {
                JSONObject jsonObject = new JSONObject(maps);
                System.out.println(jsonObject);
                if (maps.containsKey("team_money")) {

                    BigDecimal bigDecimal = jsonObject.getBigDecimal("team_money");
                    if (jsonMap.containsKey("money") && jsonMap.containsKey("type")) {
                        if (jsonMap.getBigDecimal("money").compareTo(BigDecimal.ZERO) > -1 && bigDecimal.compareTo(jsonMap.getBigDecimal("money")) > -1) {
                            Map<String, Object> trans = reportSysTransDao.SysTransSelect(jsonMap);
                            if (trans != null) {
                                JSONObject json_trans = new JSONObject(trans);
                                BigDecimal d_money = jsonMap.getBigDecimal("money").multiply(BigDecimal.valueOf(json_trans.getLong("bl")));
                                JSONObject fjson = new JSONObject();
                                fjson.put("userid", map.get("userid"));
                                if (jsonMap.getInteger("type") == 1) {
                                    fjson.put("surplus", d_money);
                                } else {
                                    fjson.put("bur_money", d_money);
                                }


                                fjson.put("team_money", jsonMap.get("money"));
                                reportSubMoneyDao.SubMoneyUpdate(fjson);
                                fjson.put("money", jsonMap.get("money"));
                                fjson.put("d_money", d_money);
                                fjson.put("type", jsonMap.get("type"));
                                subTeamMoneyDao.SubTeamMoneyInsert(fjson);
                                json.put("code", 100);
                            } else {
                                json.put("code", 103);
                                System.out.println("===3==");
                            }
                        } else {
                            json.put("code", 104);
                        }
                    } else {
                        json.put("code", 102);
                    }
                } else {
                    json.put("code", 103);
                    System.out.println("===2==");
                }
            } else {
                json.put("code", 103);
                System.out.println("===1==");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject SubTeamMoneySelect(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = subTeamMoneyDao.SubTeamMoneySelect(map);
            int count = subTeamMoneyDao.SubTeamMoneyCount(map);
            json.put("code", 100);
            json.put("team_money", list);
            json.put("total", count);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
