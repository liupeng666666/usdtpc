package com.whp.usdtpc.business.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtpc.business.Interface.SysDistrictInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : 张吉伟
 * @data : 2018/8/19 23:30
 * @descrpition :
 */
@RestController
@RequestMapping("district")
public class SysDIstrictController {
    @Autowired
    private SysDistrictInterface sysDistrictInterface;

    @PostMapping("/getDistrict")
    public JSONObject getDistrict() {
        JSONObject json = sysDistrictInterface.SysDistrictSelect();
        return json;
    }
}
