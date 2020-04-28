package com.whp.usdtpc.business.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author : 张吉伟
 * @data : 2018/7/31 14:54
 * @descrpition :
 */
@Mapper
public interface SubUserCurrencyDao {

    /**
     * @param map
     * @return
     */
    List<Map<String, Object>> SubUserCurrencySelect(Map<String, Object> map);


    /**
     * @param map
     */
    void SubUserCurrencyDel(Map<String, Object> map);

    /**
     * @param map
     */
    void SubUserCurrencyInsert(Map<String, Object> map);
}
