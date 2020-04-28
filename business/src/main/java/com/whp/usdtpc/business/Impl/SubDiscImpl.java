package com.whp.usdtpc.business.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtpc.business.Dao.BusSubMoneyDao;
import com.whp.usdtpc.business.Dao.BusSubOrderDao;
import com.whp.usdtpc.business.Dao.SubDiscDao;
import com.whp.usdtpc.business.Dao.SysSystemDao;
import com.whp.usdtpc.business.Interface.SubDiscInterface;
import com.whp.usdtpc.business.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/1 10:26
 * @descrpition :
 */
@Service
public class SubDiscImpl implements SubDiscInterface {
    @Autowired
    private SubDiscDao sysDiscDao;
    @Autowired
    private BusSubOrderDao subOrderDao;
    @Autowired
    private SysSystemDao sysSystemDao;
    @Autowired
    private BusSubMoneyDao busSubMoneyDao;

    @Override
    public JSONObject SysDiscIsStart(Map<String, Object> map, String pid) {

        JSONObject json = new JSONObject();
        try {
            System.out.println(map.toString());
            Map<String, Object> maps = sysDiscDao.SysDiscIsStart(map);
            String createtime = maps.get("createtime").toString();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(createtime);
            maps.put("createtime", date.getTime());
            Map<String, Object> system_map = sysSystemDao.SysSystemSelect();
            json.put("code", 100);
            json.put("disc", maps);
            json.put("system", system_map);
            json.put("datetime", new Date().getTime());
            Map<String, Object> map_money = new HashMap<>();
            map_money.put("userid", pid);
            Map<String, Object> map_m = busSubMoneyDao.SubMoneySelect(map_money);
            if (map_m == null) {
                json.put("surplus", 0);
            } else {
                json.put("surplus", map_m.get("surplus"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject SysDiscSelect(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            Map<String, Object> maps = sysDiscDao.SysDiscSelect(map);
            Map<String, Object> order_map = subOrderDao.SubOrderSelect(maps.get("pid").toString());
            if (order_map == null || order_map.equals(null)) {
                maps.put("purchase", 0);
            } else {
                maps.put("purchase", order_map.get("purchase"));
            }

            JSONObject jsons = new JSONObject(map);
            if (jsons.getInteger("style") == 1) {
                String fvalue = RedisUtils.yget("kline." + jsons.getString("sys_currency_id"));
                if (fvalue != "" && fvalue != null) {
                    JSONObject fjson = JSONObject.parseObject(fvalue);
                    maps.put("open", fjson.getBigDecimal("open"));
                    maps.put("close", fjson.getBigDecimal("close"));
                    maps.put("low", fjson.getBigDecimal("low"));
                    maps.put("high", fjson.getBigDecimal("high"));


                }
            }
            String value = RedisUtils.getSelect("CNY", 4);
            BigDecimal cny = new BigDecimal(value);
            BigDecimal close = new BigDecimal(maps.get("close").toString());
            maps.put("cny", close.multiply(cny).setScale(2, BigDecimal.ROUND_HALF_UP));
            json.put("disc", maps);

            json.put("code", 100);
        } catch (Exception e) {
            json.put("code", 103);
            e.printStackTrace();
        }
        return json;
    }
}
