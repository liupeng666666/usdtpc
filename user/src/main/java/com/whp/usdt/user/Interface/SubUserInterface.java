package com.whp.usdt.user.Interface;


import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface SubUserInterface {
    /**
     * 平台用户进行注册
     *
     * @param map 用户信息集
     * @return
     */
    JSONObject register(Map<String, Object> map);

    /**
     * 平台用户进行登录
     *
     * @param username 用户名
     * @param password 密码
     * @param type     登录类型 1-用户名 2- 手机号 3- email
     * @return
     */
    JSONObject login(String username, String password, int type);

    /**
     * 根据用户名查询用户信息 仅程序内使用
     *
     * @param username
     * @return
     */
    Map<String, Object> getSubUserByUserName(String username);

    /**
     * 验证用户手机号或者邮箱是否存在
     *
     * @param loginid 登录信息
     * @param type    0-email 1-手机号码
     * @return
     */
    JSONObject checkUserRegister(String loginid, int type);

    /**
     * 根据用户id查询用户详细信息
     *
     * @param userid
     * @return
     */
    JSONObject getUserInfoById(String userid);

    /**
     * 修改用户信息
     *
     * @param map
     * @return
     */
    JSONObject changUserInfoById(Map<String, Object> map);

    /**
     * 查询用户天、周、月收益率以及排行榜
     *
     * @param userid
     * @return
     */
    JSONObject getUserEarningsTopByTime(String userid);

    JSONObject getUserIncomeStatistics(String userid);

    /**
     * 验证交易密码是否正确
     *
     * @param userid
     * @param trade_password
     * @return
     */
    JSONObject checkUserTradePassword(String userid, String trade_password, int type);

    /**
     * 提交审核
     *
     * @param userid
     * @param realname
     * @param idcard
     * @param img
     * @return
     */
    JSONObject addRealnameAudit(String userid, String realname, String idcard, String img);

    /**
     * 忘记密码
     *
     * @param phone
     * @param password
     * @return
     */
    JSONObject forgetPassword(String phone, String password);


    /**
     * 发送短信
     *
     * @param userid
     * @param state
     * @return
     */
    public JSONObject FsPhone(String userid, int state);
}
