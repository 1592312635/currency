package com.minyan.dao;


import com.minyan.po.CurrencySerialPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @decription
 * @author minyan.he
 * @date 2024/6/25 11:59
 */
@Mapper
public interface CurrencySerialMapper {
  int insertSelective(CurrencySerialPO record);
}
