package com.whp.usdtpc.business.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtpc.business.Dao.SubUserCurrencyDao;
import com.whp.usdtpc.business.Dao.SysMinuteDao;
import com.whp.usdtpc.business.Interface.SubUserCurrencyInterface;
import com.whp.usdtpc.business.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/7/31 15:57
 * @descrpition :
 */
@Service
public class SubUserCurrencyImpl implements SubUserCurrencyInterface {

    @Autowired
    private SubUserCurrencyDao subUserCurrencyDao;
    @Autowired
    private SysMinuteDao sysMinuteDao;

    @Override
    public JSONObject SubUserCurrencyInsert(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            subUserCurrencyDao.SubUserCurrencyDel(map);
            if (Integer.parseInt(map.get("state").toString()) == 0) {
                subUserCurrencyDao.SubUserCurrencyInsert(map);
            }
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @Override
    public JSONObject SubUserCurrencySelect(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = subUserCurrencyDao.SubUserCurrencySelect(map);
            map.put("state", 0);
            List<Map<String, Object>> minute_list = sysMinuteDao.SysMinuteSelect(map);
            String value = RedisUtils.getSelect("CNY", 4);
            BigDecimal cny = new BigDecimal(value);
            json.put("code", 100);
            json.put("currency", list);
            json.put("minute", minute_list);
            json.put("cny", cny);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
