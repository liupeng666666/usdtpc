package com.whp.usdt.user.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SubUserDao {
    /**
     * 判断用户名或者手机号是否已存在
     *
     * @param map
     * @return
     */
    int isSysUser(Map<String, Object> map);

    /**
     * 注册用户信息
     *
     * @param map
     * @return
     */
    int register(Map<String, Object> map);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param pwd      密码
     * @param type     登录类型 1-用户名 2- 手机号 3- email
     * @return
     */
    Map<String, Object> login(@Param("username") String username, @Param("pwd") String pwd, @Param("type") int type);

    /**
     * 根据用户名查询用户信息 仅程序内部使用
     *
     * @param username
     * @return
     */
    Map<String, Object> getSubUserByUserName(@Param("username") String username);

    /**
     * 增加用户粉丝数
     *
     * @param sub_user_id
     */
    void addFansNumber(@Param("sub_user_id") String sub_user_id);

    /**
     * 增加用户关注数
     *
     * @param userid
     */
    void addFollowNumber(@Param("userid") String userid);

    /**
     * 减少用户粉丝数
     *
     * @param sub_user_id
     */
    void delFansNumber(@Param("sub_user_id") String sub_user_id);

    /**
     * 减少用户关注数
     *
     * @param userid
     */
    void delFollowNumber(@Param("userid") String userid);

    /**
     * 验证用户手机号或者邮箱是否存在
     *
     * @param loginid 登录信息
     * @param type    0-email 1-手机号码
     * @return
     */
    int checkUserRegister(@Param("loginid") String loginid, @Param("type") int type);

    /**
     * 根据用户id查询用户信息
     *
     * @param userid
     * @return
     */
    Map<String, Object> getUserInfoById(@Param("userid") String userid);

    /**
     * 修改用户信息
     *
     * @param map
     * @return
     */
    int changUserInfoById(Map<String, Object> map);

    /**
     * 查询用户天、周、月收益率以及排行榜
     *
     * @param userid
     * @param type
     * @return
     */
    Map<String, Object> getUserEarningsTopByTime(@Param("userid") String userid, @Param("type") int type, @Param("starttime") String starttime, @Param("endtime") String endtime);

    /**
     * 查询当前用户的收益率排行榜排名
     *
     * @param userid
     * @return
     */
    List<Map<String, Object>> getUserYieldRateTop(@Param("userid") String userid);

    Map<String, Object> getUserIncomeStatistics(@Param("userid") String userid);

    Map<String, Object> getUserUsdtStatistics(@Param("userid") String userid);

    /**
     * 验证交易密码是否正确
     *
     * @param userid
     * @param trade_password
     * @return
     */
    int checkUserTradePassword(@Param("userid") String userid, @Param("trade_password") String trade_password, @Param("type") int type);

    /**
     * 提交审核
     *
     * @param pid
     * @param userid
     * @param realname
     * @param idcard
     * @param img
     * @return
     */
    int addRealnameAudit(@Param("pid") String pid, @Param("userid") String userid, @Param("realname") String realname, @Param("idcard") String idcard, @Param("img") String img);

    Map<String, Object> getUserTradeOrderStatistics(@Param("userid") String userid);

    /**
     * 获取用户等级V1信息
     *
     * @return
     */
    Map<String, Object> getUserLeve1Info();

    /**
     * 根据手机号或邮箱获取用户id(仅限内部使用)
     *
     * @param referee
     * @return
     */
    String GetUserID(@Param("referee") String referee);

    /**
     * 忘记密码
     *
     * @param phone
     * @param pwd
     * @return
     */
    int forgetPassword(@Param("phone") String phone, @Param("pwd") String pwd);

    /**
     * @param pid
     * @return
     */
    Map<String, Object> ReportSubUserDan(@Param("pid") String pid);

    /**
     * @param pid
     */
    void report_team_update(@Param("pid") String pid);
}
