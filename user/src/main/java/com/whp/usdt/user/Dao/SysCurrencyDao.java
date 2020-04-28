package com.whp.usdt.user.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysCurrencyDao {
    /**
     * 获取所有币种信息
     *
     * @return
     */
    List<Map<String, Object>> getAllCurrency();
}
