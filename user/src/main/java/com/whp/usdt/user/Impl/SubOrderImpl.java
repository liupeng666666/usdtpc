package com.whp.usdt.user.Impl;

import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Dao.SubFansDao;
import com.whp.usdt.user.Dao.SubMoneyDao;
import com.whp.usdt.user.Dao.SubOrderDao;
import com.whp.usdt.user.Interface.SubOrderInterface;
import com.whp.usdt.user.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@Service
public class SubOrderImpl implements SubOrderInterface {
    @Autowired
    private SubOrderDao subOrderDao;
    @Autowired
    private SubFansDao subFansDao;
    @Autowired
    private SubMoneyDao subMoneyDao;

    @Override
    public JSONObject getProfitTop(String begintime, String endtime, String userid, int page, int number) {
        JSONObject json = new JSONObject();
        try {
            int beginnumber = (page - 1) * number;
            List<Map<String, Object>> list = subOrderDao.getProfitTop(begintime, endtime, userid, beginnumber, number);
            if (list != null && list.size() > 0) {
                int total = subOrderDao.getProfitTopCount(begintime, endtime, userid);
                json.put("code", 100);
//                for (int i = 0; i < list.size(); i++) {
//                    float income = Float.valueOf(list.get(i).get("income").toString());
//                    float trade = Float.valueOf(list.get(i).get("purchase").toString());
//                    DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
//                    String er = df.format(income / trade * 100);
//                    list.get(i).put("er", er);
//                }
                json.put("top", list);
                json.put("total", total);
            } else {
                json.put("code", 101);
                json.put("message", "系统中未找到匹配的记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }

        return json;
    }

    @Override
    public JSONObject getSubOrderSortDesc(int page, int number) {
        JSONObject json = new JSONObject();
        try {
            int beginnumber = (page - 1) * number;
            List<Map<String, Object>> list = subOrderDao.getSubOrderSortDesc(beginnumber, number);
            int total = subOrderDao.getSubOrderSortDescCount();
            if (list != null && list.size() > 0) {
                json.put("code", 100);
                for (int i = 0; i < list.size(); i++) {
                    float income = Float.valueOf(list.get(i).get("income").toString());
                    float trade = Float.valueOf(list.get(i).get("purchase").toString());
                    DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
                    String er = df.format(income / trade * 100);
                    list.get(i).put("er", er);
                }
                json.put("suborder", list);
                json.put("total", total);
            } else {
                json.put("code", 101);
                json.put("message", "系统中未找到匹配的记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject addSubOrder(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            if (map.containsKey("style") && map.containsKey("userid") && map.containsKey("sys_disc_id") && map.containsKey("purchase") && map.containsKey("range") && map.containsKey("rise_fall") && map.containsKey("beginprice")) {
                String pid = MD5Util.createId();
                map.put("pid", pid);
                subOrderDao.addSubOrder(map);
                subMoneyDao.changeSurplus(map.get("userid").toString(), Float.parseFloat(map.get("purchase").toString()));
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
    public JSONObject getFollowOrder(String userid, String follower) {
        JSONObject json = new JSONObject();
        try {
            if (userid != null && !"".equals(userid)) {
                List<Map<String, Object>> list = subOrderDao.getFollowOrder(userid, follower);
                if (list != null && list.size() > 0) {
                    json.put("code", 100);
                    json.put("order", list);
                } else {
                    json.put("code", 101);
                    json.put("message", "系统中未找到匹配的记录");
                }
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
    public JSONObject getOrderByCurrency(String userid) {
        JSONObject json = new JSONObject();
        try {
            if (userid != null && !"".equals(userid)) {
                List<Map<String, Object>> list = subOrderDao.getOrderByCurrency(userid);
                if (list != null && list.size() > 0) {
                    json.put("code", 100);
                    json.put("order", list);
                } else {
                    json.put("code", 101);
                    json.put("message", "系统中未找到匹配的记录");
                }
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
    public JSONObject getOrderByTradeSector(String userid, int style, String begintime, String endtime, int page, int number, String state) {
        JSONObject json = new JSONObject();
        try {
            if (userid != null && !"".equals(userid)) {
                List<Map<String, Object>> list = subOrderDao.getOrderByTradeSector(userid, style, begintime, endtime, page, number, state);

                int total = subOrderDao.getOrderByTradeSectorCnt(userid, style, begintime, endtime, state);

                if (list != null && list.size() > 0) {
                    json.put("code", 100);
                    json.put("total", total);
                    json.put("order", list);
                } else {
                    json.put("code", 101);
                    json.put("message", "系统中未找到匹配的记录");
                }
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
    public JSONObject getRateOfReturnByTime(String userid, String begintime, String endtime) {
        JSONObject json = new JSONObject();
        try {
            if (userid != null && !"".equals(userid)) {
                List<Map<String, Object>> list = subOrderDao.getRateOfReturnByTime(userid, begintime, endtime);
                if (list != null && list.size() > 0) {
                    json.put("code", 100);
                    json.put("order", list);
                } else {
                    json.put("code", 101);
                    json.put("message", "系统中未找到匹配的记录");
                }
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
    public JSONObject getIncomeAmountByTime(String userid, String begintime, String endtime) {
        JSONObject json = new JSONObject();
        try {
            if (userid != null && !"".equals(userid)) {
                List<Map<String, Object>> list = subOrderDao.getIncomeAmountByTime(userid, begintime, endtime);
                if (list != null && list.size() > 0) {
                    json.put("code", 100);
                    json.put("order", list);
                } else {
                    json.put("code", 101);
                    json.put("message", "系统中未找到匹配的记录");
                }
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
    public JSONObject getNetValueBalanceById(String userid, String begintime, String endtime) {
        JSONObject json = new JSONObject();
        try {
            if (userid != null && !"".equals(userid)) {
                List<Map<String, Object>> list = subOrderDao.getNetValueBalanceById(userid, begintime, endtime);
                if (list != null && list.size() > 0) {
                    json.put("code", 100);
                    json.put("order", list);
                } else {
                    json.put("code", 101);
                    json.put("message", "系统中未找到匹配的记录");
                }
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
    public JSONObject getSubOrderByUserPage(String userid, int page, int number, String style) {
        JSONObject json = new JSONObject();
        try {
            int beginnumber = (page - 1) * number;
            List<Map<String, Object>> list = subOrderDao.getSubOrderByUserPage(userid, style, beginnumber, number);
            int total = subOrderDao.getSubOrderByUserPageCount(userid, style);
            if (list != null && list.size() > 0) {
                json.put("code", 100);
                for (int i = 0; i < list.size(); i++) {
                    float income = Float.valueOf(list.get(i).get("income").toString());
                    float trade = Float.valueOf(list.get(i).get("purchase").toString());
                    DecimalFormat df = new DecimalFormat("0.0000");//格式化小数，不足的补0
                    String er = df.format(income / trade * 100);
                    list.get(i).put("er", er);
                }
                json.put("suborder", list);
                json.put("total", total);
            } else {
                json.put("code", 101);
                json.put("message", "系统中未找到匹配的记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }
}
