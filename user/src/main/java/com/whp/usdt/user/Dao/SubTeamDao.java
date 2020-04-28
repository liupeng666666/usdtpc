package com.whp.usdt.user.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/11/27 21:47
 * @descrpition :
 */
@Mapper
public interface SubTeamDao {

    void sub_team_insert(Map<String, Object> map);
}
