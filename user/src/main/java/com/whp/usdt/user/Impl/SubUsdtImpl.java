package com.whp.usdt.user.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Dao.SubMoneyDao;
import com.whp.usdt.user.Dao.SubUsdtDao;
import com.whp.usdt.user.Interface.SubUsdtInterface;
import com.whp.usdt.user.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubUsdtImpl implements SubUsdtInterface {
    @Autowired
    private SubUsdtDao subUsdtDao;
    @Autowired
    private SubMoneyDao subMoneyDao;

    @Override
    public JSONObject getSysWallet() {
        JSONObject json = new JSONObject();
        try {
            Map<String, Object> map = subUsdtDao.getSysWallet();
            if (map != null) {
                json.put("code", 100);
                json.put("wallet", map.get("wallet"));
                json.put("pid", map.get("pid"));
            } else {
                json.put("code", 101);
                json.put("message", "操作异常，请重新操作");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "数据库连接失败请重新操作");
        }
        return json;
    }

    @Override
    public JSONObject addSubUsdt(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            if (map.containsKey("userid") && map.get("userid") != null && map.containsKey("sub_wallet_id") && map.get("sub_wallet_id") != null && map.containsKey("style") && map.containsKey("money") && map.containsKey("trade") && Float.parseFloat(map.get("money").toString()) > 0) {
                String pid = MD5Util.createId();
                map.put("pid", pid);
                if (Integer.parseInt(map.get("style").toString()) == 2) {
                    int num = subUsdtDao.addSubUsdt(map);
                    if (num > 0) {

                        float surplus = 0 - (Float.parseFloat(map.get("money").toString()) + Float.parseFloat(map.get("trade").toString()));
                        if (Integer.parseInt(map.get("codeid").toString()) == 31) {
                            subMoneyDao.changeSurplus(map.get("userid").toString(), surplus);
                        } else if (Integer.parseInt(map.get("codeid").toString()) == 695) {
                            subMoneyDao.changeSurplusBru(map.get("userid").toString(), surplus);
                        }


                        json.put("code", 100);

                    } else {
                        json.put("code", 101);
                        json.put("message", "添加失败");
                    }
                } else {
                    json.put("code", 102);
                    json.put("message", "请求参数不正确");
                }
            } else {
                json.put("code", 102);
                json.put("message", "请求参数不正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "数据库连接失败请重新操作");
        }
        return json;
    }

    @Override
    public JSONObject getSubUsdtByIdStyle(String userid, String style, int page, int number, String codeid) {
        JSONObject json = new JSONObject();
        try {
            if (userid != null && !"".equals(userid)) {
                int beginnumber = (page - 1) * number;
                List<Map<String, Object>> list = subUsdtDao.getSubUsdtByIdStyle(userid, style, beginnumber, number, codeid);
                if (list != null && list.size() > 0) {
                    json.put("code", 100);
                    json.put("usdt", list);
                } else {
                    json.put("code", 101);
                    json.put("message", "系统未查询相关记录");
                }
            } else {
                json.put("code", 102);
                json.put("message", "请求参数有误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "数据库连接失败请重新操作");
        }
        return json;
    }

    @Override
    public JSONObject getFreezeUsdt(String userid) {
        JSONObject json = new JSONObject();
        try {
            Map<String, Object> map = subUsdtDao.getFreezeUsdt(userid);
            if (map != null) {
                json.put("code", 100);
                json.put("freeze", map);
            } else {
                json.put("code", 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "数据库连接失败请重新操作");
        }
        return json;
    }

}
