package com.whp.usdtpc.report.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/11/20 16:25
 * @descrpition :
 */
@Mapper
public interface ReportSubExtractDao {

    public List<Map<String, Object>> SubExtractSelect(Map<String, Object> map);

    public int SubExtractCount(Map<String, Object> map);
}
