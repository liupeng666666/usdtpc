package com.whp.usdtpc.business.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtpc.business.Interface.SysRangeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/1 21:42
 * @descrpition :
 */
@RestController
@RequestMapping("sysrange")
public class SysRangeController {
    @Autowired
    private SysRangeInterface sysRangeInterface;

    @PostMapping("SysRangeSelect")
    public JSONObject SysRangeSelect(@RequestParam Map<String, Object> map) {
        JSONObject json = sysRangeInterface.SysRangeSelect(map);
        return json;
    }
}
