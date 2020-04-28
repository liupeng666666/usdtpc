package com.whp.usdtpc.business.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Interface.SubMoneyInterface;
import com.whp.usdtpc.business.Dao.BusSubMoneyDao;
import com.whp.usdtpc.business.Dao.BusSubOrderDao;
import com.whp.usdtpc.business.Dao.SubDiscDao;
import com.whp.usdtpc.business.Dao.SysRangeDao;
import com.whp.usdtpc.business.Interface.BusSubOrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/1 23:21
 * @descrpition :
 */
@Service
public class BusSubOrderImpl implements BusSubOrderInterface {
    @Value("${usdt.shouxufei}")
    private double shouxufei;
    @Autowired
    private BusSubOrderDao subOrderDao;

    @Autowired
    private BusSubMoneyDao subMoneyDao;
    @Autowired
    private SubDiscDao subDiscDao;
    @Autowired
    private SysRangeDao sysRangeDao;

    @Override
    public JSONObject SubOrderUpdate(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            subOrderDao.SubOrderUpdate(map);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }

        return json;
    }

    @Override
    public JSONObject SubOrderDatetime(String pid) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = subOrderDao.SubOrderDatetime(pid);
            json.put("code", 100);
            json.put("datetime", list);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }

        return json;
    }

    @Override
    public JSONObject SubOrderBQUSelect(String userid) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = subOrderDao.SubOrderBQUSelect(userid);
            json.put("code", 100);
            json.put("order", list);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }

        return json;
    }

    @Override
    public JSONObject SubOrderAdd(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {

            Map<String, Object> maps = subMoneyDao.SubMoneySelect(map);
            if (map == null) {
                json.put("code", 201);
            } else {
                JSONObject cjson = new JSONObject(map);
                JSONObject fjson = new JSONObject(maps);
                if (fjson.getBigDecimal("surplus").compareTo((cjson.getBigDecimal("purchase").multiply(BigDecimal.valueOf(1 + shouxufei)))) == -1) {
                    json.put("code", 202);
                } else {
                    if (cjson.getFloat("purchase") > 0) {
                        map.put("trade", cjson.getBigDecimal("purchase").multiply(BigDecimal.valueOf(shouxufei)));
                        map.put("q_surplus", fjson.getBigDecimal("surplus").subtract((cjson.getBigDecimal("purchase").multiply(BigDecimal.valueOf(1 + shouxufei)))));
                        Map<String, Object> map1 = subDiscDao.SysDiscSelectPid(map);
                        Map<String, Object> map2 = sysRangeDao.SysRangeDan(map.get("range").toString());
                        if (map2 != null && map2.containsKey("range")) {
                            map.put("range", map2.get("range"));
                            map.put("phase", map1.get("phase"));
                            map.put("currency", map1.get("currency"));
                            map.put("minute", map1.get("minute"));
                            map.put("minute_style", map1.get("minute_style"));
                            if ("1".equals(map1.get("minute_style"))) {
                                if (map.containsKey("icrease") && map2.containsKey("rise_range")) {
                                    map.put("icrease", Float.parseFloat(map2.get("rise_range").toString()) / 100);
                                } else {
                                    json.put("code", 102);
                                    return json;
                                }
                            }
                            subOrderDao.SubOrderAdd(map);
                            map.put("surplus", (cjson.getBigDecimal("purchase").multiply(BigDecimal.valueOf(1 + shouxufei))));
                            subMoneyDao.SubMoneyUpdate(map);
                            List<Map<String, Object>> list = subMoneyDao.SubTeamSelect(map.get("userid").toString());
                            for (Map<String, Object> team_map : list) {
                                JSONObject zjson = new JSONObject();
                                zjson.put("pid", team_map.get("userid"));
                                zjson.put("money", cjson.getBigDecimal("purchase"));
                                subMoneyDao.SubMoneyTeamUpdate(zjson);
                            }
                            json.put("code", 100);
                        } else {
                            json.put("code", 102);
                        }
                    } else {
                        json.put("code", 102);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }

        return json;
    }

    @Override
    public JSONObject SubOrderUserSelect(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = subOrderDao.SubOrderUserSelect(map);
            json.put("code", 100);
            json.put("order", list);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }

        return json;
    }
}
