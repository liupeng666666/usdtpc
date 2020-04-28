package com.whp.usdtpc.WebIM.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdtpc.WebIM.Interface.SubGroupInterface;
import com.whp.usdtpc.WebIM.Utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/9/20 18:05
 * @descrpition :
 */
@RestController
@RequestMapping("HuanXin")
public class HuanXinController {
    @Autowired
    private SubGroupInterface subGroupInterface;

    @GetMapping("LiaoTianShi")
    public JSONObject LiaoTianShi(String group, String userid) {
        JSONObject jsonObject = new JSONObject();
        try {

            Map<String, String> headers = new HashMap<>(2);
            String token = token();
            System.out.println("token:" + token);
            headers.put("Authorization", "Bearer " + token);
            String url = "https://a1.easemob.com/1112180904146878/currency/chatrooms/" + group + "/users/" + userid;
            JSONObject json = new JSONObject();
            String resp = HttpUtil.jsonPost(url, headers, json.toJSONString());
            JSONObject jsons = JSONObject.parseObject(resp);
            System.out.println(jsons);
            if (jsons.containsKey("data")) {
                JSONObject datajson = jsons.getJSONObject("data");
                if (datajson.containsKey("result")) {
                    if (datajson.getBoolean("result")) {
                        jsonObject.put("code", 100);

                    } else {
                        jsonObject.put("code", 105);
                    }
                } else {
                    jsonObject.put("code", 104);
                }
            } else {
                jsonObject.put("code", 104);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("code", 103);
        }
        return jsonObject;
    }

    @GetMapping("QunZu")
    public JSONObject QunZu(String group, String userid) {
        JSONObject jsonObject = new JSONObject();
        try {

            Map<String, String> headers = new HashMap<>(2);
            String token = token();
            System.out.println("token:" + token);
            headers.put("Authorization", "Bearer " + token);
            String url = "https://a1.easemob.com/1112180904146878/currency/chatgroups/" + group + "/users/" + userid;
            JSONObject json = new JSONObject();
            String resp = HttpUtil.jsonPost(url, headers, json.toJSONString());
            JSONObject jsons = JSONObject.parseObject(resp);
            System.out.println("群：" + jsons);
            if (jsons.containsKey("data")) {
                JSONObject datajson = jsons.getJSONObject("data");
                System.out.println("群2：" + datajson);
                if (datajson.containsKey("result")) {
                    System.out.println("群3：" + datajson.getBoolean("result"));
                    if (datajson.getBoolean("result")) {
                        jsonObject.put("code", 100);

                    } else {
                        jsonObject.put("code", 105);
                    }
                } else {
                    jsonObject.put("code", 104);
                }
            } else {
                jsonObject.put("code", 104);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("code", 103);
        }
        return jsonObject;
    }

    @PostMapping("HaoYou")
    public JSONObject haoyou(String sub_user_id, String userid) {
        JSONObject jsonObject = new JSONObject();
        try {

            Map<String, String> headers = new HashMap<>(2);
            String token = token();
            System.out.println("token:" + token);
            headers.put("Authorization", "Bearer " + token);
            String url = "https://a1.easemob.com/1112180904146878/currency/users/" + userid + "/contacts/users/" + sub_user_id;
            JSONObject json = new JSONObject();
            String resp = HttpUtil.jsonPost(url, headers, json.toJSONString());
            JSONObject jsons = JSONObject.parseObject(resp);
            System.out.println("好友：" + jsons);
            if (jsons.containsKey("entities")) {
                jsonObject.put("code", 100);
            } else {
                jsonObject.put("code", 104);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("code", 103);
        }
        return jsonObject;
    }

    public String token() {
        Map<String, String> headers = new HashMap<>(2);
        String url = "https://a1.easemob.com/1112180904146878/currency/token";
        JSONObject json = new JSONObject();
        json.put("grant_type", "client_credentials");
        json.put("client_id", "YXA61w5dkLQBEei1E3UecRiYsQ");
        json.put("client_secret", "YXA6KxsUT-Kq29C6zS_yVPbZ7-ws42I");
        String resp = HttpUtil.jsonPost(url, headers, json.toJSONString());
        JSONObject jsonObject = JSONObject.parseObject(resp);
        String token = jsonObject.getString("access_token");
        return token;
    }
}
