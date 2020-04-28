package com.whp.usdtpc.report.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/11/22 15:00
 * @descrpition :
 */
@Mapper
public interface ReportSubTeamMoneyDao {

    public void SubTeamMoneyInsert(Map<String, Object> map);

    public List<Map<String, Object>> SubTeamMoneySelect(Map<String, Object> map);

    public int SubTeamMoneyCount(Map<String, Object> map);
}
