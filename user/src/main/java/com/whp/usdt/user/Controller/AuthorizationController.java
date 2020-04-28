package com.whp.usdt.user.Controller;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;
import com.whp.usdt.user.Interface.*;
import com.whp.usdt.user.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

@Controller
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    private SubUserInterface subUserInterface;
    @Autowired
    private SubOrderInterface subOrderInterface;
    @Autowired
    private SubTraderEarningsInterface subTraderEarningsInterface;
    @Autowired
    private SysAdInterface sysAdInterface;
    @Autowired
    private SysClassInterface sysClassInterface;
    @Autowired
    private SysDiscInterface sysDiscInterface;
    @Autowired
    private SysNewsInterface sysNewsInterface;
    @Autowired
    private SubFansInterfac subFansInterfac;
    @Autowired
    JavaMailSender jms;
    @Autowired
    private SysVersionInterface sysVersionInterface;


    /**
     * 用户进行注册
     *
     * @param map 用户注册信息
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject register(@RequestParam Map<String, Object> map) {
        JSONObject json = new JSONObject();
        if (map.containsKey("phone")) {
            String value = Redis.get_yzm("phone." + map.get("phone"), 7);
            System.out.println("value:" + value);
            System.out.println("phone:" + map.get("yzm"));
            if (!map.get("yzm").equals(value)) {
                json.put("code", 141);
                return json;
            }
            Redis.del_yzm("phone." + map.get("phone"), 7);
        }
        if (map.containsKey("email")) {
            String value = Redis.get_yzm("email." + map.get("email"), 7);
            if (!map.get("yzm").equals(value)) {
                json.put("code", 141);
                return json;
            }
            Redis.del_yzm("email." + map.get("email"), 7);
        }
        json = subUserInterface.register(map);
        return json;
    }

    /**
     * 用户进行登录
     *
     * @param username 用户名
     * @param password 密码
     * @param type     类型1-用户名 2- 手机号 3- 邮箱
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject login(String username, String password, String code, int type, String uuid, HttpServletRequest request) {
        System.out.println("loginin");
        long startTime = System.currentTimeMillis();   //获取开始时间
        JSONObject json = new JSONObject();
        if (request.getSession().getAttribute("code") != null) {
            String scode = request.getSession().getAttribute("code").toString();
            if (!scode.equals(code)) {
                json.put("code", 107);
                json.put("message", "验证码不正确");
            } else {
                json = subUserInterface.login(username, password, type);
                if (json.getInteger("code") == 100) {
                    String token = JWTUtil.sign(json.getJSONObject("users").getString("pid"), password, json.getJSONObject("users").getString("pid"), request.getSession().getId());
                    json.put("token", token);
                    String rkey = json.getJSONObject("users").getString("pid") + "_1";
                    Redis.setLoginPlatformInfo(rkey, uuid);
                }
            }
        } else {
            json.put("code", 107);
            json.put("message", "验证码不正确");
        }
        long endTime = System.currentTimeMillis(); //获取结束时间

//        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
        return json;
    }

    @RequestMapping(value = "/scanLogin", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject scanLogin(String username, String password, int type, String uuid, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        json = subUserInterface.login(username, password, type);
        if (json.getInteger("code") == 100) {
            String token = JWTUtil.sign(json.getJSONObject("users").getString("pid"), password, json.getJSONObject("users").getString("pid"), request.getSession().getId());
            json.put("token", token);
            String rkey = json.getJSONObject("users").getString("pid") + "_1";
            Redis.setLoginPlatformInfo(rkey, uuid);
        }
        return json;
    }

    /**
     * APP用户进行登录
     *
     * @param username 用户名
     * @param password 密码
     * @param type     类型1-用户名 2- 手机号 3- 邮箱
     * @return
     */
    @RequestMapping(value = "/appLogin", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject appLogin(String username, String password, int type, String uuid, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        json = subUserInterface.login(username, password, type);
        if (json.getInteger("code") == 100) {
            String token = JWTUtil.sign(json.getJSONObject("users").getString("pid"), password, json.getJSONObject("users").getString("pid"), request.getSession().getId());
            json.put("token", token);
            String rkey = json.getJSONObject("users").getString("pid") + "_2";
            Redis.setLoginPlatformInfo(rkey, uuid);
        }
        return json;
    }

    /**
     * 重新获取token
     *
     * @param username
     * @param password
     * @param type
     * @param request
     * @return
     */
    @RequestMapping(value = "tokenLoseEfficacy", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject tokenLoseEfficacy(String username, String password, int type, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        json = subUserInterface.login(username, password, type);
        if (json.getInteger("code") == 100) {
            String token = JWTUtil.sign(json.getJSONObject("users").getString("pid"), password, json.getJSONObject("users").getString("pid"), request.getSession().getId());
            json.put("token", token);
        }
        return json;
    }

    /**
     * 验证用户手机号或者邮箱是否存在
     *
     * @param loginid 登录信息
     * @param type    0-email 1-手机号码,2验证码
     * @return
     */
    @RequestMapping(value = "/checkUserRegister", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkUserRegister(String loginid, int type) {
        JSONObject json = new JSONObject();
        json = subUserInterface.checkUserRegister(loginid, type);
        return json;
    }

    @RequestMapping(value = "/SubYanZhengMa", method = RequestMethod.GET)
    @ResponseBody
    public void SubYanZhengMa(HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("我进来啦");
        try {
            HttpSession session = request.getSession();
            int width = 180;
            int height = 40;
            String imgType = "jpeg";
            OutputStream output = response.getOutputStream();
            String code = GraphicHelper.create(width, height, imgType, output, request);
            System.out.println(request.getSession().getAttribute("code"));
//            session.setAttribute("code",code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/getUserLoginQrCode", method = RequestMethod.GET)
    @ResponseBody
    public void getUserLoginQrCode(String content, HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            int width = 180;
            int height = 40;
            String imgType = "jpeg";
            OutputStream output = response.getOutputStream();
            boolean flag = QrCodeCreateUtil.createQrCode(output, content, 900, imgType);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/getGoogleImg", method = RequestMethod.GET)
    @ResponseBody
    public void getGoogleImg(String content, HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            int width = 180;
            int height = 40;
            String imgType = "jpeg";
            OutputStream output = response.getOutputStream();
            boolean flag = QrCodeCreateUtil.createQrCode(output, content, 900, imgType);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
     * 根据位置检索广告信息
     *
     * @param location 位置类型
     * @return
     */
    @RequestMapping(value = "/getAdByLocation", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getAdByLocation(int location) {
        JSONObject json = new JSONObject();
        json = sysAdInterface.getAdByLocation(location);
        return json;
    }

    /**
     * 根据类型获取分类
     *
     * @param style 类型 0:新闻分类，1公告分类，2关于我们
     * @return
     */
    @RequestMapping(value = "/getClassByStyle", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getClassByStyle(String style) {
        JSONObject json = new JSONObject();
        json = sysClassInterface.getClassByStyle(style);
        return json;
    }

    /**
     * 根据交易区查询盘口信息和数据
     *
     * @param style
     * @return
     */
    @RequestMapping(value = "/getDiscInfoByDist", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getDiscInfoByDist(String style) {
        JSONObject json = new JSONObject();
        json = sysDiscInterface.getDiscInfoByDist(style);
        return json;
    }

    /**
     * 根据分类查询新闻
     *
     * @param sys_class_id 新闻分类id
     * @return
     */
    @RequestMapping(value = "/getSysNewsByClass", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getSysNewsByClass(String sys_class_id, int page, int number) {
        JSONObject json = new JSONObject();
        json = sysNewsInterface.getSysNewsByClass(sys_class_id, page, number);
        return json;
    }

    /**
     * 时间倒序查询24小时快讯
     *
     * @return
     */
    @RequestMapping(value = "/getSysNewsFlash", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getSysNewsFlash(int page, int number) {
        JSONObject json = new JSONObject();
        json = sysNewsInterface.getSysNewsFlash(page, number);
        return json;
    }

    /**
     * 根据用户id 查询用户详细信息
     *
     * @param userid
     * @return
     */
    @RequestMapping(value = "/getUserInfoById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getUserInfoById(String userid) {
        JSONObject json = new JSONObject();
        json = subUserInterface.getUserInfoById(userid);
        return json;
    }

    /**
     * 查询用户天、周、月收益率以及排行榜
     *
     * @param userid
     * @return
     */
    @RequestMapping(value = "getUserEarningsTopByTime", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getUserEarningsTopByTime(String userid) {
        JSONObject json = new JSONObject();
        json = subUserInterface.getUserEarningsTopByTime(userid);
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
     * 查询该用户下当前所有可跟随订单
     *
     * @param userid 用户id
     * @return
     */
    @RequestMapping(value = "/getFollowOrder", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getFollowOrder(String userid, String follower) {
        JSONObject json = new JSONObject();
        json = subOrderInterface.getFollowOrder(userid, follower);
        return json;
    }

    /**
     * 根据用户id查询币种数据
     *
     * @param userid 用户id
     * @return
     */
    @RequestMapping(value = "/getOrderByCurrency", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getOrderByCurrency(String userid) {
        JSONObject json = new JSONObject();
        json = subOrderInterface.getOrderByCurrency(userid);
        return json;
    }

    /**
     * 根据交易区和用户id查询最近的订单
     *
     * @param userid
     * @param style
     * @return
     */
    @RequestMapping(value = "/getOrderByTradeSector", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getOrderByTradeSector(String userid, int style, String begintime, String endtime, int page, int number, String state) {
        JSONObject json = new JSONObject();
        json = subOrderInterface.getOrderByTradeSector(userid, style, begintime, endtime, page, number, state);
        return json;
    }

    /**
     * 获取收益率统计
     *
     * @param userid    用户id
     * @param begintime 开始时间
     * @param endtime   结束时间
     * @return
     */
    @RequestMapping(value = "/getRateOfReturnByTime", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getRateOfReturnByTime(String userid, String begintime, String endtime) {
        JSONObject json = new JSONObject();
        json = subOrderInterface.getRateOfReturnByTime(userid, begintime, endtime);
        return json;
    }

    /**
     * 获取收益金额统计
     *
     * @param userid    用户id
     * @param begintime 开始时间
     * @param endtime   结束时间
     * @return
     */
    @RequestMapping(value = "/getIncomeAmountByTime", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getIncomeAmountByTime(String userid, String begintime, String endtime) {
        JSONObject json = new JSONObject();
        json = subOrderInterface.getIncomeAmountByTime(userid, begintime, endtime);
        return json;
    }

    /**
     * 获取用户收入统计
     *
     * @param userid 用户id
     * @return
     */
    @RequestMapping(value = "/getUserIncomeStatistics", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getUserIncomeStatistics(String userid) {
        JSONObject json = new JSONObject();
        json = subUserInterface.getUserIncomeStatistics(userid);
        return json;
    }

    /**
     * 获取净值余额统计
     *
     * @param userid 用户id
     * @return
     */
    @RequestMapping(value = "/getNetValueBalanceById", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getNetValueBalanceById(String userid, String begintime, String endtime) {
        JSONObject json = new JSONObject();
        json = subOrderInterface.getNetValueBalanceById(userid, begintime, endtime);
        return json;
    }

    /**
     * 单个新闻详情
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/getNewsMessage", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getNewsMessage(String pid) {
        JSONObject json = new JSONObject();
        json = sysNewsInterface.getNewsMessage(pid);
        return json;
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @RequestMapping(value = "/sendSmsCode", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject sendSmsCode(String phone) {
        JSONObject json = new JSONObject();
        String code = MD5Util.createSmscode();
        Redis.set_yzm("phone." + phone, 7, code + "");
        try {
            String result = Sms.sendSms(phone, code);
            if ("0".equals(result)) {
                json.put("code", 100);
            } else {
                json.put("code", 101);
            }
        } catch (Exception e) {
            json.put("code", 103);
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @RequestMapping(value = "/BruSendSmsCode", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject BruSendSmsCode(String phone) {
        JSONObject json = new JSONObject();
        String code = MD5Util.createSmscode();
        Redis.set_yzm("phone." + phone, 7, code + "");
        try {
            String result = Sms.BrusendSms(phone, code);
            if ("0".equals(result)) {
                json.put("code", 100);
            } else {
                json.put("code", 101);
            }
        } catch (Exception e) {
            json.put("code", 103);
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping(value = "/sendGoogleSmsCode", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject sendGoogleSmsCode(String phone) {
        JSONObject json = new JSONObject();
        String code = MD5Util.createSmscode();
        Redis.set_yzm("phone." + phone, 7, code + "");
        try {
            String result = Sms.sendGoogleSms(phone, code);
            if ("0".equals(result)) {

                json.put("code", 100);
            } else {
                json.put("code", 101);
            }
        } catch (Exception e) {
            json.put("code", 103);
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 发送邮箱验证码
     *
     * @param email
     * @return
     */
    @RequestMapping(value = "/sendEmailCode", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject sendEmailCode(String email) {
        JSONObject json = new JSONObject();
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.smtp.socketFactory.port", "465");
            properties.setProperty("mail.smtp.port", "465");
            String code = MD5Util.createSmscode();
            //建立邮件消息
            SimpleMailMessage mainMessage = new SimpleMailMessage();
            String senduser = PropertiesUtils.KeyValue("spring.mail.username");
            //发送者
            mainMessage.setFrom(senduser);
            //接收者
            mainMessage.setTo(email);
            //发送的标题
            mainMessage.setSubject("验证码");
            //发送的内容
            mainMessage.setText("您的账号刚刚进行了安全设置操作如非您本人授权操作，请立即检查您的账号安全并联系我们。Bucrrency【币元网】验证码：" + code);
            jms.send(mainMessage);
            Redis.set_yzm("email." + email, 7, code + "");
            json.put("emailcode", code);
            json.put("code", 100);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

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

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadImg(@RequestParam(value = "img", required = false) MultipartFile img, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String url = ImgUtil.url(img, 200, 200, 2);
        if (url != null && !"".equals(url)) {
            json.put("url", url);
            json.put("code", 100);
        } else {
            json.put("code", 101);
        }
        return json;
    }

    @RequestMapping(value = "/uploadImgBG", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadImgBG(@RequestParam(value = "img", required = false) MultipartFile img, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String url = ImgUtil.url(img, 720, 344, 7);
        if (url != null && !"".equals(url)) {
            json.put("url", url);
            json.put("code", 100);
        } else {
            json.put("code", 101);
        }
        return json;
    }

    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject forgetPassword(String phone, String password, String yzm) {
        JSONObject json = new JSONObject();
        String value = Redis.get_yzm("phone." + phone, 7);
        if (!yzm.equals(value)) {
            json.put("code", 141);
            return json;
        }
        Redis.del_yzm("phone." + phone, 7);
        System.out.println("====");
        json = subUserInterface.forgetPassword(phone, password);
        return json;
    }

    @RequestMapping(value = "/getScanLoginUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getScanLoginUserInfo(String uuid, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        try {
            JSONObject rjson = Redis.getScanLoginUserInfo(uuid);
            if (rjson == null) {
                json.put("code", 101);
            } else {
                json.put("code", 100);
                json.put("users", rjson);
                String rkey = json.getJSONObject("users").getString("pid") + "_1";
                Redis.setLoginPlatformInfo(rkey, request.getSession().getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 103);
        }
        return json;
    }

    @RequestMapping(value = "/ShengJi", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ShengJi(int type) {
        JSONObject json = sysVersionInterface.SysVersion(type);
        return json;
    }

}
