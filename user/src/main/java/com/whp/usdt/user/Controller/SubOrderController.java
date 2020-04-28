package com.whp.usdt.user.Controller;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Interface.SubOrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author 吴敬鹏
 * @date 2018-07-13
 * @Deprecated 用户交易订单类
 */
@Controller
@RequestMapping("/suborder")
public class SubOrderController {
    @Autowired
    private SubOrderInterface subOrderInterface;

    /**
     * 根据时间段查询收益最大排行榜
     *
     * @param begintime 开始时间
     * @param endtime   结束时间
     * @param userid    用户id
     * @return
     */
    @RequestMapping(value = "/getProfitTop", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getProfitTop(String begintime, String endtime, String userid, int page, int number) {
        JSONObject json = new JSONObject();
        json = subOrderInterface.getProfitTop(begintime, endtime, userid, page, number);
        return json;
    }

    /**
     * 获取最近交易订单
     *
     * @param page   当前页码
     * @param number 拉取条数
     * @return
     */
    @RequestMapping(value = "/getSubOrderSortDesc", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getSubOrderSortDesc(int page, int number) {
        JSONObject json = new JSONObject();
        json = subOrderInterface.getSubOrderSortDesc(page, number);
        return json;
    }

    /**
     * 添加新的订单
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/addSubOrder", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addSubOrder(@RequestParam Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json = subOrderInterface.addSubOrder(map);
        return json;
    }

    /**
     * 根据用户分页查询最近的下单记录
     *
     * @param userid 用户id
     * @param page   当前页码
     * @param number 条数
     * @return
     */
    @RequestMapping(value = "/getSubOrderByUserPage", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getSubOrderByUserPage(String userid, int page, int number, String style) {
        JSONObject json = new JSONObject();
        json = subOrderInterface.getSubOrderByUserPage(userid, page, number, style);
        return json;
    }
}
