package com.whp.usdt.user.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Dao.SubMoneyDao;
import com.whp.usdt.user.Dao.SubTraderEarningsDao;
import com.whp.usdt.user.Dao.SubUserDao;
import com.whp.usdt.user.Dao.SubWalletDao;
import com.whp.usdt.user.Interface.SubTraderEarningsInterface;
import com.whp.usdt.user.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubTraderEarningsImpl implements SubTraderEarningsInterface {
    @Autowired
    private SubTraderEarningsDao subTraderEarningsDao;
    @Autowired
    private SubMoneyDao subMoneyDao;
    @Autowired
    private SubWalletDao subWalletDao;
    @Autowired
    private SubUserDao subUserDao;

    @Override
    public JSONObject getSubTraderEarningsTop(String begintime, String endtime, int page, int number) {
        JSONObject json = new JSONObject();
        try {
            int beginnumber = (page - 1) * number;
            List<Map<String, Object>> list = subTraderEarningsDao.getSubTraderEarningsTop(begintime, endtime, beginnumber, number);
            if (list != null && list.size() > 0) {
                json.put("code", 100);
                json.put("earnings", list);
            } else {
                json.put("code", 101);
                json.put("message", "系统无相匹配记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject getSubTraderFollowerEarnings(String begintime, String endtime, int page, int number, String userid) {
        JSONObject json = new JSONObject();
        try {
            int beginnumber = (page - 1) * number;
            List<Map<String, Object>> list = subTraderEarningsDao.getSubTraderFollowerEarnings(begintime, endtime, beginnumber, number, userid);
            if (list != null && list.size() > 0) {
                int total = subTraderEarningsDao.getSubTraderFollowerEarningsCount(begintime, endtime);
                json.put("total", total);
                json.put("follow", list);
                json.put("code", 100);
            } else {
                json.put("code", 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject getTraderEarningsByType(String userid) {
        JSONObject json = new JSONObject();
        try {
            if (userid != null && !"".equals(userid)) {
                Map<String, Object> map = subTraderEarningsDao.getTraderEarningsByType(userid, 1);
                Map<String, Object> map1 = subTraderEarningsDao.getTraderEarningsByType(userid, 2);
                Map<String, Object> map2 = subTraderEarningsDao.getTraderEarningsByType(userid, 3);
                if (map != null && map.containsKey("earnings")) {
                    json.put("order", map.get("earnings"));
                } else {
                    json.put("order", 0);
                }
                if (map1 != null && map1.containsKey("earnings")) {
                    json.put("select", map1.get("earnings"));
                } else {
                    json.put("select", 0);
                }
                if (map2 != null && map2.containsKey("earnings")) {
                    json.put("brokerage", map2.get("earnings"));
                } else {
                    json.put("brokerage", 0);
                }
                json.put("code", 100);
            } else {
                json.put("code", 102);
                json.put("message", "请求语法错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject getUserPayRecordByType(String pay_userid, String userid, String type, int page, int number) {
        JSONObject json = new JSONObject();
        try {
            int beginnumber = (page - 1) * number;
            List<Map<String, Object>> list = subTraderEarningsDao.getUserPayRecordByType(pay_userid, userid, type, beginnumber, number);
            if (list != null && list.size() > 0) {
                int total = subTraderEarningsDao.getUserPayRecordByTypeCount(pay_userid, userid, type);
                json.put("total", total);
                json.put("pay", list);
                json.put("code", 100);
            } else {
                json.put("code", 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject getTradeEarningsByUserIdType(String userid, int type, int page, int number) {
        JSONObject json = new JSONObject();
        try {
            int beginnumber = (page - 1) * number;
            List<Map<String, Object>> list = subTraderEarningsDao.getTradeEarningsByUserIdType(userid, type, beginnumber, number);
            if (list != null && list.size() > 0) {
                int total = subTraderEarningsDao.getTradeEarningsByUserIdTypeCount(userid, type);
                json.put("total", total);
                json.put("earnings", list);
                json.put("code", 100);
            } else {
                json.put("code", 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject addTradeEarnings(String userid, String orderid, String follower, int type, float money) {
        JSONObject json = new JSONObject();
        try {
            String pid = MD5Util.createId();
            float surplus = 0 - money;
            int num = subMoneyDao.changeSurplus(follower, surplus);
            int num2 = subMoneyDao.changeSurplus(userid, money);
            if (num > 0 && num2 > 0) {
                subTraderEarningsDao.addTradeEarnings(pid, userid, money, type, orderid, follower);
                Map<String, Object> m = subMoneyDao.getMoneyInfo(follower);
                Map<String, Object> map = new HashMap<String, Object>();
                String pid2 = MD5Util.createId();
                map.put("pid", pid2);
                map.put("surplus", m.get("surplus"));
                map.put("income", money);
                map.put("loss", 0);
                map.put("state", 3);
                map.put("trade", 0);
                map.put("userid", userid);
                map.put("orderid", orderid);
                map.put("pay_userid", follower);
                subWalletDao.addSubDetailed(map);
                subUserDao.addFansNumber(userid);
            }
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject SubDetailedShou(String userid, int page, int num) {

        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> list = subTraderEarningsDao.SubDetailedShou(userid, (page - 1) * num, num);
            int count = subTraderEarningsDao.SubDetailedShouCount(userid);
            json.put("code", 100);
            json.put("detailed", list);
            json.put("total", count);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
