package com.minyan.dao;

import com.minyan.po.CurrencyOrderPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @decription 订单表mapper
 * @author minyan.he
 * @date 2024/6/25 11:59
 */
@Mapper
public interface CurrencyOrderMapper {
  int insertSelective(CurrencyOrderPO record);
}
