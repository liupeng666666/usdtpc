package com.whp.usdt.user.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Interface.SysDiscInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 吴敬鹏
 * @date 2018-07-13
 * @Deprecated AB盘数据操作类
 */
@Controller
@RequestMapping("/sysdisc")
public class SysDiscController {
    @Autowired
    private SysDiscInterface sysDiscInterface;

    /**
     * 根据交易区查询盘口信息和数据
     *
     * @param style
     * @return
     */
    @RequestMapping(value = "/getDiscInfoByDist", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getDiscInfoByDist(String style) {
        JSONObject json = new JSONObject();
        json = sysDiscInterface.getDiscInfoByDist(style);
        return json;
    }

}
