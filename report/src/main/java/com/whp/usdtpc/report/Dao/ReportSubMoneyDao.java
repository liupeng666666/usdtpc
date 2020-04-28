package com.whp.usdtpc.report.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/11/20 14:11
 * @descrpition :
 */
@Mapper
public interface ReportSubMoneyDao {

    public Map<String, Object> SubMoneySelect(Map<String, Object> map);

    public Map<String, Object> SubMoneyTeam(Map<String, Object> map);

    public void SubMoneyUpdate(Map<String, Object> map);
}
