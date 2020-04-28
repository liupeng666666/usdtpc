package com.whp.usdt.user.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whp.usdt.user.Dao.SubMoneyDao;
import com.whp.usdt.user.Dao.SubTeamDao;
import com.whp.usdt.user.Dao.SubUsdtDao;
import com.whp.usdt.user.Dao.SubUserDao;
import com.whp.usdt.user.Interface.SubUserInterface;
import com.whp.usdt.user.utils.MD5Util;
import com.whp.usdt.user.utils.Redis;
import com.whp.usdt.user.utils.Sms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SubUserImpl implements SubUserInterface {

    @Autowired
    private SubUserDao subUserDao;
    @Autowired
    private SubMoneyDao subMoneyDao;
    @Autowired
    private SubUsdtDao subUsdtDao;
    @Autowired
    private SubTeamDao subTeamDao;

    @Override
    public JSONObject register(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            if (map.containsKey("trade_password") && map.get("trade_password") != null && map.containsKey("password") && map.get("password") != null && ((map.containsKey("phone") && map.get("phone") != null) || (map.containsKey("email") && map.get("email") != null))) {
                int count = subUserDao.isSysUser(map);
                if (count == 0) {
                    Map<String, Object> grade = subUserDao.getUserLeve1Info();
                    map.put("sys_grade_id", grade.get("pid"));
                    map.put("money", grade.get("money"));
                    String pid = MD5Util.createId();
                    map.put("pid", pid);
                    String pwd = MD5Util.MD5(map.get("password").toString());
                    map.put("password", pwd);
                    String tpwd = MD5Util.MD5(map.get("trade_password").toString());
                    map.put("trade_password", tpwd);
                    if (map.containsKey("phone") && map.get("phone") != null) {
                        map.put("username", map.get("phone"));
                        map.put("regtype", 0);
                    } else {
                        map.put("username", map.get("email"));
                        map.put("regtype", 1);
                    }
                    if (!map.containsKey("channel") || map.get("channel") == null) {
                        map.put("channel", 0);
                    }
                    String referee = map.get("referee").toString();
                    if (referee.indexOf("@") > -1 || isNumeric(referee)) {
                        String sub_user_id = subUserDao.GetUserID(map.get("referee").toString());
                        map.put("sub_user_id", sub_user_id);
                    } else {
                        String sub_user_id = Redis.getInviteCode(referee);
                        map.put("sub_user_id", sub_user_id);
                    }

                    int number = subUserDao.register(map);
                    subMoneyDao.addMoney(map);
                    Map<String, Object> usdt = new HashMap<String, Object>();
                    String upid = MD5Util.createId();
                    usdt.put("pid", upid);
                    usdt.put("style", 0);
                    usdt.put("sys_wallet_id", 0);
                    usdt.put("money", grade.get("money"));
                    usdt.put("sub_user_id", pid);
                    usdt.put("trade", 0);
                    subUsdtDao.addSubUsdtPass(usdt);
                    System.out.println(map.get("sub_user_id"));
                    System.out.println(pid);
                    if (map.containsKey("sub_user_id") && !"null".equals(map.get("sub_user_id")) && map.get("sub_user_id") != null) {
                        digui(map.get("sub_user_id").toString(), pid, 1);
                    }
                    json.put("code", "100");
                    json.put("userid", pid);
                } else {
                    json.put("code", "104");
                    json.put("message", "该用户已注册");
                }
            } else {
                json.put("code", "102");
                json.put("message", "请求数据语法错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", "103");
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    @Override
    public JSONObject login(String username, String password, int type) {
        JSONObject json = new JSONObject();
        try {
            //判断上传的用户名、密码是否为空
            if (username != null && !"".equals(username) && password != null && !"".equals(password) && (type == 1 || type == 2 || type == 3)) {
                String pwd = MD5Util.MD5(password);
                Map<String, Object> user = subUserDao.login(username, pwd, type);
                if (user != null && user.size() > 0) {
                    json.put("code", 100);
                    json.put("users", user);
                } else {
                    json.put("code", 101);
                    json.put("message", "系统中未找到匹配的记录");
                }
            } else {
                json.put("code", 102);
                json.put("message", "请求数据语法错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public Map<String, Object> getSubUserByUserName(String username) {
        return subUserDao.getSubUserByUserName(username);
    }

    @Override
    public JSONObject checkUserRegister(String loginid, int type) {
        JSONObject json = new JSONObject();
        try {
            if (type == 2) {
                String value = Redis.getGoogleInviteCode(loginid);
                if (value == null || value == "" || value.equals("") || value.equals(null)) {
                    json.put("code", 100);
                } else {
                    json.put("code", 104);
                    json.put("message", "您输入的邀请码存在");

                }
            } else {
                int count = subUserDao.checkUserRegister(loginid, type);
                if (count > 0) {
                    json.put("code", 104);
                    json.put("message", "您输入的手机号或email已存在");
                } else {
                    json.put("code", 100);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject getUserInfoById(String userid) {
        JSONObject json = new JSONObject();
        try {
            if (userid != null && !"".equals(userid)) {
                Map<String, Object> user = subUserDao.getUserInfoById(userid);
                if (user.containsKey("usdt_money") && user.containsKey("bru_money")) {
                    String value = Redis.get_yzm("kline.bru", 0);
                    JSONObject fjson = JSONObject.parseObject(value);
                    if (fjson.containsKey("close")) {
                        float money = Float.parseFloat(user.get("bru_money").toString()) * fjson.getFloat("close");
                        float usdt_money = Float.parseFloat(user.get("usdt_money").toString()) + money;
                        user.put("usdt_money", usdt_money);
                    }
                }
                json.put("code", 100);
                json.put("user", user);
            } else {
                json.put("code", "102");
                json.put("message", "请求参数错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject changUserInfoById(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        try {
            if (map.containsKey("userid") && map.get("userid") != null) {
                Map<String, Object> fmap = subUserDao.getUserInfoById(map.get("userid").toString());
                if (fmap != null) {
                    if (map.containsKey("password") && map.get("password") != null) {
                        String value = Redis.get_yzm("phone." + fmap.get("phone"), 7);
                        if (!map.get("yzm").equals(value)) {
                            json.put("code", 141);
                            return json;
                        }
                        Redis.del_yzm("phone." + fmap.get("phone"), 7);
                        String pwd = MD5Util.MD5(map.get("password").toString());
                        map.put("password", pwd);
                    }
                    if (map.containsKey("trade_password") && map.get("trade_password") != null) {
                        String value = Redis.get_yzm("phone." + fmap.get("phone"), 7);
                        if (!map.get("yzm").equals(value)) {
                            json.put("code", 141);
                            return json;
                        }
                        Redis.del_yzm("phone." + fmap.get("phone"), 7);
                        String tpwd = MD5Util.MD5(map.get("trade_password").toString());
                        map.put("trade_password", tpwd);
                    }
                    int count = subUserDao.changUserInfoById(map);
                    if (count > 0) {
                        json.put("code", 100);
                        json.put("message", "修改成功");
                    }
                } else {
                    json.put("code", "102");
                    json.put("message", "请求参数错误");
                }
            } else {
                json.put("code", "102");
                json.put("message", "请求参数错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject getUserEarningsTopByTime(String userid) {
        JSONObject json = new JSONObject();
        try {
            Calendar c = Calendar.getInstance();
            Map<String, Object> day = subUserDao.getUserEarningsTopByTime(userid, 1, null, null);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            c.setTime(new Date());
            c.add(Calendar.DATE, -6);
            Date d = c.getTime();
            String starttime = simpleDateFormat.format(d) + " 00:00:00";
            Map<String, Object> week = subUserDao.getUserEarningsTopByTime(userid, 2, starttime, null);
            c.setTime(new Date());
            c.add(Calendar.DATE, -30);
            Date d1 = c.getTime();
            String Mstarttime = simpleDateFormat.format(d1) + " 00:00:00";
            Map<String, Object> month = subUserDao.getUserEarningsTopByTime(userid, 3, Mstarttime, null);
            System.out.println("monthTime:" + Mstarttime);
            if (day != null && day.containsKey("income") && day.containsKey("purchase")) {
                json.put("dayincome", day.get("income"));
                json.put("daypurchase", day.get("purchase"));
                json.put("dayer", day.get("er"));
            } else {
                json.put("dayincome", 0);
                json.put("daypurchase", 0);
                json.put("dayer", 0);
            }
            if (week != null && week.containsKey("income") && week.containsKey("purchase")) {
                json.put("weekincome", week.get("income"));
                json.put("weekpurchase", week.get("purchase"));
                json.put("weeker", week.get("er"));
            } else {
                json.put("weekincome", 0);
                json.put("weekpurchase", 0);
                json.put("weeker", 0);
            }
            if (month != null && month.containsKey("income") && month.containsKey("purchase")) {
                json.put("monthincome", month.get("income"));
                json.put("monthpurchase", month.get("purchase"));
                json.put("monther", month.get("er"));
            } else {
                json.put("monthincome", 0);
                json.put("monthpurchase", 0);
                json.put("monther", 0);
            }
            List<Map<String, Object>> list = subUserDao.getUserYieldRateTop(userid);
            json.put("top", list.size());
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject getUserIncomeStatistics(String userid) {
        JSONObject json = new JSONObject();
        try {
            if (userid != null && !"".equals(userid)) {
                Map<String, Object> map = subUserDao.getUserIncomeStatistics(userid);
                Map<String, Object> map1 = subUserDao.getUserUsdtStatistics(userid);
                Map<String, Object> map2 = subUserDao.getUserTradeOrderStatistics(userid);
                if (map != null) {
                    json.put("code", 100);
                    json.put("maxincome", map.get("maxincome"));
                    json.put("minincome", map.get("minincome"));
                    json.put("num", map.get("num"));
                    json.put("followincome", map.get("followincome"));
                    if (map1 != null) {
                        json.put("amoney", map1.get("amoney"));
                        json.put("bmoney", map1.get("bmoney"));
                    } else {
                        json.put("amoney", 0);
                        json.put("bmoney", 0);
                    }
                    if (map2 != null) {
                        json.put("aqu", map2.get("anum"));
                        json.put("bqu", map2.get("bnum"));
                        json.put("cqu", map2.get("cnum"));
                        json.put("zongnum", map2.get("num"));
                    } else {
                        json.put("aqu", 0);
                        json.put("bqu", 0);
                        json.put("cqu", 0);
                        json.put("zongnum", 0);
                    }
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
    public JSONObject checkUserTradePassword(String userid, String trade_password, int type) {
        JSONObject json = new JSONObject();
        try {
            if (userid != null && trade_password != null) {
                trade_password = MD5Util.MD5(trade_password);
                int num = subUserDao.checkUserTradePassword(userid, trade_password, type);
                if (num > 0) {
                    json.put("code", 100);
                } else {
                    json.put("code", 101);
                    json.put("message", "系统未查到相关记录");
                }
            } else {
                json.put("code", 102);
                json.put("message", "请求错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        return json;
    }

    @Override
    public JSONObject addRealnameAudit(String userid, String realname, String idcard, String img) {
        JSONObject json = new JSONObject();
        try {
            String pid = MD5Util.createId();
            int num = subUserDao.addRealnameAudit(pid, userid, realname, idcard, img);
            if (num > 0) {
                Map<String, Object> map = new HashMap<>();
                map.put("real_name", "3");
                map.put("userid", userid);
                subUserDao.changUserInfoById(map);
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
    public JSONObject forgetPassword(String phone, String password) {
        System.out.println("==-=--===");
        JSONObject json = new JSONObject();
        try {
            String pid = MD5Util.createId();
            String pwd = MD5Util.MD5(password);

            subUserDao.forgetPassword(phone, pwd);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
            json.put("message", "连接数据库失败，无法获取相关数据");
        }
        System.out.println("json:" + json);
        return json;
    }


    public void digui(String userid, String sub_user_id, int level) {
        JSONObject json = new JSONObject();
        json.put("userid", userid);
        json.put("sub_user_id", sub_user_id);
        json.put("level", level);
        subTeamDao.sub_team_insert(json);
        subUserDao.report_team_update(userid);
        if (level < 3) {
            Map<String, Object> map = subUserDao.ReportSubUserDan(userid);
            if (map != null) {
                if (map.containsKey("pid") && map.containsKey("sub_user_id")) {
                    digui(map.get("sub_user_id").toString(), sub_user_id, level + 1);
                }

            }
        }
    }


    @Override
    public JSONObject FsPhone(String userid, int state) {
        JSONObject json = new JSONObject();
        try {
            Map<String, Object> map = subUserDao.getUserInfoById(userid);
            if (map != null) {
                String code = MD5Util.createSmscode();
                Sms.sendFbPhone(map.get("phone").toString(), code, state);
                Redis.set_yzm("phone." + map.get("phone"), 7, code);
                json.put("code", 100);
            } else {
                json.put("code", 102);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }
}
