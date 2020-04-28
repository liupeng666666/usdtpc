package com.whp.usdtpc.business.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtpc.business.Dao.SysRangeDao;
import com.whp.usdtpc.business.Dao.SysRiseFallDao;
import com.whp.usdtpc.business.Interface.SysRangeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/1 21:40
 * @descrpition :
 */
@Service
public class SysRangeImpl implements SysRangeInterface {
    @Autowired
    private SysRangeDao sysRangeDao;
    @Autowired
    private SysRiseFallDao sysRiseFallDao;

    @Override
    public JSONObject SysRangeSelect(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = sysRangeDao.SysRangeSelect(map);
            List<Map<String, Object>> list_rise = sysRiseFallDao.SysRiseFallSelect(map);
            json.put("code", 100);
            json.put("range", list);
            json.put("risefall", list_rise);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
