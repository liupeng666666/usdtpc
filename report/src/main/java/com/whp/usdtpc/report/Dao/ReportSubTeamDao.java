package com.whp.usdtpc.report.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/11/20 16:11
 * @descrpition :
 */
@Mapper
public interface ReportSubTeamDao {

    public List<Map<String, Object>> SubTeamSelect(Map<String, Object> map);

    public int SubTeamCount(Map<String, Object> map);
}
