package com.whp.usdtpc.business.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whp.usdtpc.business.Dao.SysDistrictDao;
import com.whp.usdtpc.business.Interface.SysDistrictInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/19 23:08
 * @descrpition :
 */
@Service
public class SysDistrictImpl implements SysDistrictInterface {
    @Autowired
    private SysDistrictDao sysDistrictDao;

    @Override
    public JSONObject SysDistrictSelect() {
        JSONObject json = new JSONObject();
        try {

            List<Map<String, Object>> list = sysDistrictDao.SysDistrictSelect(1);
            List<Map<String, Object>> list_two = sysDistrictDao.SysDistrictSelect(2);
            JSONArray array = new JSONArray();
            for (Map<String, Object> map : list) {
                JSONObject jsons = new JSONObject();
                jsons.put("value", map.get("district"));
                JSONArray array1 = new JSONArray();
                for (Map<String, Object> map_two : list_two) {
                    if (map_two.get("pid").equals(map.get("id"))) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("value", map_two.get("district"));
                        array1.add(jsonObject);
                    }
                }
                jsons.put("childs", array1);
                array.add(jsons);
            }
            json.put("dq", array);
            json.put("code", 100);
        } catch (Exception e) {
            json.put("code", 103);
        }
        System.out.println("json:" + json);
        return json;
    }
}
