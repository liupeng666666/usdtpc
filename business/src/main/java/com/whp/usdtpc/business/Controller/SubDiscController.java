package com.whp.usdtpc.business.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.utils.JWTUtil;
import com.whp.usdtpc.business.Dao.SysMinuteDao;
import com.whp.usdtpc.business.Interface.SubDiscInterface;
import com.whp.usdtpc.business.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author : 张吉伟
 * @data : 2018/8/1 10:37
 * @descrpition :
 */
@RestController
@RequestMapping("subdisc")
public class SubDiscController {
    @Autowired
    private SubDiscInterface sysDiscInterface;
    @Autowired
    private SysMinuteDao sysMinuteDao;

    @PostMapping("SubDiscOrder")
    public JSONObject SubDiscOrder(@RequestParam Map<String, Object> map) {
        JSONObject json = sysDiscInterface.SysDiscSelect(map);
        return json;
    }

    @PostMapping("SubDiscKline")
    public JSONObject SubDiscKline(@RequestParam Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            int start = 0;
            if (map.containsKey("start")) {
                start = Integer.parseInt(map.get("start").toString());
            }
            long time = new Date().getTime();
            String key = "A." + map.get("sys_currency_id").toString() + "." + map.get("sys_minute_id").toString();
            List<String> disc = RedisUtils.lrange(key, Integer.parseInt(map.get("num").toString()), start);
            json.put("code", 100);
            json.put("kline", disc);
            if (map.containsKey("state")) {
                if (Integer.parseInt(map.get("state").toString()) == 1) {
                    JSONArray array_m = new JSONArray();
                    Map<String, Object> minute_map = new HashMap<>();
                    List<Map<String, Object>> list = sysMinuteDao.SysMinuteSelect(minute_map);
                    time = new Date().getTime();
                    int num = 0;
                    for (Map<String, Object> minute : list) {
                        if (minute != null) {
                            if (minute.get("pid").toString().equals(map.get("sys_minute_id"))) {
                                num = Integer.parseInt(minute.get("minute").toString());
                            }
                        }
                    }
                    int d_size = disc.size();
                    for (Map<String, Object> minute : list) {
                        System.out.println("minute==" + minute);
                        if (minute != null) {
                            JSONObject json_minute = new JSONObject(minute);
                            if (json_minute.getInteger("style") == 0) {
                                if (!json_minute.getString("pid").equals(map.get("sys_minute_id"))) {
                                    int fnum = Integer.parseInt(minute.get("minute").toString());
                                    double bfb = (double) num / (double) fnum;
                                    int z_num = (int) Math.ceil(d_size * bfb);
                                    String fkey = "A." + map.get("sys_currency_id").toString() + "." + json_minute.getString("pid");
                                    List<String> z_disc = RedisUtils.lrange(fkey, z_num, start);
                                    JSONObject jsonObject = new JSONObject();
                                    JSONArray array = new JSONArray();
                                    for (int i = d_size - 1; i >= 0; i--) {
                                        int s_num = (int) Math.ceil(i * bfb);
                                        if (s_num < z_disc.size()) {
                                            array.add(JSONObject.parseObject(z_disc.get(s_num)).getBigDecimal("close"));
                                        } else {
                                            if (z_disc.size() != 0) {
                                                array.add(JSONObject.parseObject(z_disc.get(z_disc.size() - 1)).getBigDecimal("close"));
                                            }
                                        }
                                    }
                                    jsonObject.put("key", fnum);
                                    jsonObject.put("value", array);
                                    array_m.add(jsonObject);
                                }
                            }

                        }

                    }
                    json.put("kline_q", array_m);
                }
            }
            long time1 = new Date().getTime();
            System.out.println("时间：" + (time1 - time));
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @PostMapping("SubDiscIsStart")
    public JSONObject SubDiscIsStart(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = sysDiscInterface.SysDiscIsStart(map, pid);
        return json;
    }

    @PostMapping("SubDiscKlineNew")
    public JSONObject SubDiscKlineNew(@RequestParam Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            String key = "A." + map.get("sys_currency_id").toString() + "." + map.get("sys_minute_id").toString();
            List<String> disc = RedisUtils.lrange(key, Integer.parseInt(map.get("num").toString()), Integer.parseInt(map.get("start").toString()));
            json.put("disc", disc);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
