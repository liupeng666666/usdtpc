package com.whp.usdt.user.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Dao.SysCurrencyDao;
import com.whp.usdt.user.Dao.SysDiscDao;
import com.whp.usdt.user.Interface.SysDiscInterface;
import com.whp.usdt.user.utils.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysDiscImpl implements SysDiscInterface {
    @Autowired
    private SysDiscDao sysDiscDao;
    @Autowired
    private SysCurrencyDao sysCurrencyDao;

    @Override
    public JSONObject getDiscInfoByDist(String style) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> sclist = sysCurrencyDao.getAllCurrency();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String area = "";
            if ("0".equals(style)) {
                area = "A.";
            } else if ("1".equals(style)) {
                area = "B.";
            }
            for (int i = 0; i < sclist.size(); i++) {
                Map<String, Object> map = sysDiscDao.getDiscInfoByDist(style, sclist.get(i).get("pid").toString());
                if (map != null && !map.isEmpty()) {
                    map.put("name", sclist.get(i).get("name").toString());
                    map.put("y_name", sclist.get(i).get("y_name").toString());
                    map.put("symbol", sclist.get(i).get("symbol").toString());
                    map.put("pid", sclist.get(i).get("pid").toString());
                    map.put("sys_minute_id", map.get("sys_minute_id").toString());
                    map.put("minute", map.get("minute").toString());
                    String key = area + sclist.get(i).get("pid").toString() + "." + map.get("sys_minute_id").toString();
                    JSONObject redisjson = Redis.getKlinByCurrency(key, 24, map.get("sys_minute_id").toString(), Integer.parseInt(map.get("minute").toString()));
                    if (redisjson.getInteger("code") == 100) {
                        JSONArray redisarray = redisjson.getJSONArray("kline");
                        String[] datearray = new String[redisarray.size()];
                        float[] dataarray = new float[redisarray.size()];
                        for (int j = 0; j < redisarray.size(); j++) {
                            Date date = new Date();
                            date.setTime(redisarray.getJSONObject(j).getLong("createtime"));
                            datearray[j] = format.format(date);
                            dataarray[j] = redisarray.getJSONObject(j).getFloat("close");
                        }
                        map.put("datearray", datearray);
                        map.put("dataarray", dataarray);


//                        String [] datearray = new String[redisarray.size()];
//                        float [][] dataarray = new float [redisarray.size()][4];
//                        for (int j = 0 ; j<redisarray.size();j++){
//                            Date date = new Date();
////                            System.out.println(redisarray.getJSONObject(j).get("phase")+"==="+redisarray.getJSONObject(j).getLong("ts"));
//                            date.setTime(redisarray.getJSONObject(j).getLong("createtime"));
//                            datearray[j] = format.format(date);
//                            float [] array = new float[4];
//                            array[0] = redisarray.getJSONObject(j).getFloat("open");
//                            array[1] = redisarray.getJSONObject(j).getFloat("close");
//                            array[2] = redisarray.getJSONObject(j).getFloat("low");
//                            array[3] = redisarray.getJSONObject(j).getFloat("high");
//                            dataarray[j] =array;
//                        }
//                        map.put("datearray",datearray);
//                        map.put("dataarray",dataarray);
                        float gains = redisarray.getJSONObject(redisarray.size() - 1).getFloat("close") - redisarray.getJSONObject(0).getFloat("close");
                        map.put("gains", gains);
                        map.put("nowprice", redisarray.getJSONObject(redisarray.size() - 1).getFloat("close"));
                    }
                    list.add(map);
                }
            }
            json.put("code", 100);
            json.put("discinfo", list);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "数据库异常，请重新获取数据");
        }
        return json;
    }

}
