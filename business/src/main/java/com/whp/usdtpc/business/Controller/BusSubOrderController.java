package com.whp.usdtpc.business.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.utils.JWTUtil;
import com.whp.usdtpc.business.Interface.BusSubOrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/1 23:25
 * @descrpition :
 */
@RestController
@RequestMapping("BusSubOrder")
public class BusSubOrderController {
    @Autowired
    private BusSubOrderInterface subOrderInterface;

    @PostMapping("BusSubOrderSelect")
    public JSONObject BusSubOrderSelect(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        if (Integer.parseInt(map.get("type").toString()) == 0) {
            String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
            map.put("userid", userid);
        }
        JSONObject json = subOrderInterface.SubOrderUserSelect(map);
        return json;
    }

    @PostMapping("BusSubOrderAdd")
    public JSONObject BusSubOrderAdd(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        map.put("userid", userid);
        JSONObject json = subOrderInterface.SubOrderAdd(map);
        return json;
    }

    @PostMapping("BusSubOrderUpdate")
    public JSONObject BusSubOrderUpdate(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        map.put("isfollow", "0");
        JSONObject json = subOrderInterface.SubOrderUpdate(map);
        return json;
    }

    @PostMapping("SubOrderDatetime")
    public JSONObject SubOrderDatetime(HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = subOrderInterface.SubOrderDatetime(pid);
        return json;
    }

    @PostMapping("SubOrderBQUSelect")
    public JSONObject SubOrderBQUSelect(HttpServletRequest request) {
        String pid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        JSONObject json = subOrderInterface.SubOrderBQUSelect(pid);
        return json;
    }
}
