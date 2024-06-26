package com.minyan.dao;

import com.minyan.po.CurrencyAccountPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @decription
 * @author minyan.he
 * @date 2024/6/25 11:59
 */
@Mapper
public interface CurrencyAccountMapper {
  List<CurrencyAccountPO> selectListSelective(Map<String, Object> map);

  int insertSelective(CurrencyAccountPO record);

  int updateByUserIdAndCurrencyType(@Param("userId")String userId,@Param("currencyType") Integer currencyType,@Param("addCurrency") BigDecimal currency);
}
