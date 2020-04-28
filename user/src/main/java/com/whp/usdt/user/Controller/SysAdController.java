package com.whp.usdt.user.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Interface.SysAdInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 吴敬鹏
 * @date 2018-07-13
 * @Deprecated 广告操作类
 */
@Controller
@RequestMapping("/sysad")
public class SysAdController {
    @Autowired
    private SysAdInterface sysAdInterface;

    /**
     * 根据位置检索广告信息
     *
     * @param location 位置类型
     * @return
     */
    @RequestMapping(value = "/getAdByLocation", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getAdByLocation(int location) {
        JSONObject json = new JSONObject();
        json = sysAdInterface.getAdByLocation(location);
        return json;
    }
}
