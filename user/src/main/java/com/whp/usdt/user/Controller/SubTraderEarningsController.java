package com.whp.usdt.user.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Interface.SubTraderEarningsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 吴敬鹏
 * @date 2018-07-16
 * @Deprecated 交易员收益操作类
 */
@Controller
@RequestMapping("/subtraderearnings")
public class SubTraderEarningsController {
    @Autowired
    private SubTraderEarningsInterface subTraderEarningsInterface;

    /**
     * 根据时间周期分页查询交易员收益排行榜
     *
     * @param begintime 开始时间
     * @param endtime   结束时间
     * @param page      当前页
     * @param number    条数
     * @return
     */
    @RequestMapping(value = "/getSubTraderEarningsTop", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getSubTraderEarningsTop(String begintime, String endtime, int page, int number) {
        JSONObject json = new JSONObject();
        json = subTraderEarningsInterface.getSubTraderEarningsTop(begintime, endtime, page, number);
        return json;
    }

    /**
     * 根据时间周期分页查询跟随者收益
     *
     * @param begintime 开始时间
     * @param endtime   结束时间
     * @param page      当前页码
     * @param number    查询条数
     * @return
     */
    @RequestMapping(value = "/getSubTraderFollowerEarnings", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getSubTraderFollowerEarnings(String begintime, String endtime, int page, int number, String userid) {
        JSONObject json = new JSONObject();
        json = subTraderEarningsInterface.getSubTraderFollowerEarnings(begintime, endtime, page, number, userid);
        return json;
    }

    /**
     * 根据类型查询交易员收入
     *
     * @return
     */
    @RequestMapping(value = "/getTraderEarningsByType", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getTraderEarningsByType(String userid) {
        JSONObject json = new JSONObject();
        json = subTraderEarningsInterface.getTraderEarningsByType(userid);
        return json;
    }

    /**
     * 根据用户id和类型获取用户交易记录
     *
     * @param userid
     * @param type   类型：1：下单收入，2：好友奖励，3：查看交易员，4：充值，5：体现，6：赠送，
     * @return
     */
    @RequestMapping(value = "/getUserPayRecordByType", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getUserPayRecordByType(String pay_userid, String userid, String type, int page, int number) {
        JSONObject json = new JSONObject();
        json = subTraderEarningsInterface.getUserPayRecordByType(pay_userid, userid, type, page, number);
        return json;
    }

    /**
     * 根据交易类型查询交易员收入
     *
     * @param userid
     * @param type
     * @return
     */
    @RequestMapping(value = "/getTradeEarningsByUserIdType", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getTradeEarningsByUserIdType(String userid, int type, int page, int number) {
        JSONObject json = new JSONObject();
        json = subTraderEarningsInterface.getTradeEarningsByUserIdType(userid, type, page, number);
        return json;
    }

    /**
     * 添加一条新的交易员收益
     *
     * @param userid
     * @param orderid
     * @param follower
     * @param type
     * @return
     */
    @RequestMapping(value = "/addTradeEarnings", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addTradeEarnings(String userid, String orderid, String follower, int type, float money) {
        JSONObject json = new JSONObject();
        json = subTraderEarningsInterface.addTradeEarnings(userid, orderid, follower, type, money);
        return json;
    }

    @RequestMapping(value = "/SubDetailedShou", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject SubDetailedShou(String userid, String orderid, int page, int num) {
        JSONObject json = new JSONObject();
        json = subTraderEarningsInterface.SubDetailedShou(userid, page, num);
        return json;
    }
}
