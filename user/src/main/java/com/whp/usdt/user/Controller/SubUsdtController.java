package com.whp.usdt.user.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Dao.SubUserDao;
import com.whp.usdt.user.Interface.SubUsdtInterface;
import com.whp.usdt.user.utils.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/subusdt")
public class SubUsdtController {
    @Autowired
    private SubUsdtInterface subUsdtInterface;
    @Autowired
    private SubUserDao subUserDao;

    /**
     * 获取系统钱包地址
     *
     * @return
     */
    @RequestMapping(value = "/getSysWallet", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getSysWallet() {
        JSONObject json = new JSONObject();
        json = subUsdtInterface.getSysWallet();
        return json;
    }

    /**
     * 添加一条USDT记录
     *
     * @return
     */
    @RequestMapping(value = "/addSubUsdt", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addSubUsdt(@RequestParam Map<String, Object> map) {
        JSONObject json = new JSONObject();
        String nr = Redis.get_yzm("WALLET.3", 7);
        if ("1".equals(nr)) {
            json.put("code", 171);
            return json;
        }
        Map<String, Object> mapa = subUserDao.ReportSubUserDan(map.get("userid").toString());
        if (mapa != null) {
            String value = Redis.get_yzm("phone." + mapa.get("phone"), 7);
            if (!map.get("yzm").equals(value)) {
                json.put("code", 141);
                return json;
            }
            Redis.del_yzm("phone." + mapa.get("phone"), 7);
        } else {
            json.put("code", 141);
            return json;
        }

        json = subUsdtInterface.addSubUsdt(map);
        return json;
    }

    /**
     * 根据用户id 和类型查询用户操作USDT记录
     *
     * @param userid 用户id
     * @param style  动作
     * @return
     */
    @RequestMapping(value = "/getSubUsdtByIdStyle", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getSubUsdtByIdStyle(String userid, String style, int page, int number, String codeid) {
        JSONObject json = new JSONObject();
        json = subUsdtInterface.getSubUsdtByIdStyle(userid, style, page, number, codeid);
        return json;
    }

    @RequestMapping(value = "/getFreezeUsdt", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getFreezeUsdt(String userid) {
        JSONObject json = new JSONObject();
        json = subUsdtInterface.getFreezeUsdt(userid);
        return json;
    }
}
