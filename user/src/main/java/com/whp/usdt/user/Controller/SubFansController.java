package com.whp.usdt.user.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Interface.SubFansInterfac;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 吴敬鹏
 * @date 2018-07-13
 * @Deprecated 用户粉丝操作类
 */
@Controller
@RequestMapping("/subfans")
public class SubFansController {
    @Autowired
    private SubFansInterfac subFansInterfac;

    /**
     * 添加新的粉丝
     *
     * @param sub_user_id 要关注人id
     * @param userid      用户id
     * @return
     */
    @RequestMapping(value = "addFans", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addFans(String sub_user_id, String userid) {
        JSONObject json = new JSONObject();
        json = subFansInterfac.addFans(sub_user_id, userid);
        return json;
    }

    /**
     * 用户取消关注
     *
     * @param sub_user_id 要关注人id
     * @param userid      用户id
     * @return
     */
    @RequestMapping(value = "/delFans", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delFans(String sub_user_id, String userid) {
        JSONObject json = new JSONObject();
        json = subFansInterfac.delFans(sub_user_id, userid);
        return json;
    }

    /**
     * 根据用户id查询粉丝
     *
     * @param userid 用户id
     * @return
     */
    @RequestMapping(value = "/getFansByUserid", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getFansByUserid(String userid) {
        JSONObject json = new JSONObject();
        json = subFansInterfac.getFansByUserid(userid);
        return json;
    }

    /**
     * 根据用户id查询关注者
     *
     * @param userid 用户id
     * @return
     */
    @RequestMapping(value = "/getConcernByUserid", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getConcernByUserid(String userid) {
        JSONObject json = new JSONObject();
        json = subFansInterfac.getConcernByUserid(userid);
        return json;
    }

    /**
     * 根据用户id查询关注的交易员
     *
     * @param userid 用户id
     * @return
     */
    @RequestMapping(value = "/getFollowerTraderById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getFollowerTraderById(String userid) {
        JSONObject json = new JSONObject();
        json = subFansInterfac.getFollowerTraderById(userid);
        return json;
    }
}
