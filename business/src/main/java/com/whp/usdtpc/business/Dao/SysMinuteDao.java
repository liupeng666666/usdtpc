package com.whp.usdtpc.business.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/8/1 9:47
 * @descrpition :
 */
@Mapper
public interface SysMinuteDao {

    List<Map<String, Object>> SysMinuteSelect(Map<String, Object> map);
}
