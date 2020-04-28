package com.whp.usdtpc.report.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/11/22 16:52
 * @descrpition :
 */
@Mapper
public interface ReportSysTransDao {

    public Map<String, Object> SysTransSelect(Map<String, Object> map);

    public List<Map<String, Object>> SysTrans();
}
