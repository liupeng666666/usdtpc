package com.whp.usdtpc.business.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.utils.JWTUtil;
import com.whp.usdtpc.business.Interface.SubUserCurrencyInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/7/31 20:40
 * @descrpition :
 */
@RestController
@RequestMapping("/subUserCurrency")
public class SubUserCurrencyController {
    @Autowired
    private SubUserCurrencyInterface subUserCurrencyInterface;

    @PostMapping("/SubUserCurrencySelect")
    public JSONObject SubUserCurrencySelect(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String userid = "";
        if (authorization == null || authorization.equals("null")) {
            userid = "";
        } else {
            userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        }

        map.put("userid", userid);
        JSONObject json = subUserCurrencyInterface.SubUserCurrencySelect(map);
        return json;
    }

    @PostMapping("/SubUserCurrencyInsert")
    public JSONObject SubUserCurrencyInsert(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String userid = JWTUtil.getUsername(request.getHeader("Authorization"), "pid");
        map.put("userid", userid);
        JSONObject json = subUserCurrencyInterface.SubUserCurrencyInsert(map);
        return json;
    }
}
