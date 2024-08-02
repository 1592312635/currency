package com.minyan.dao;

import com.minyan.po.CurrencyAccountPO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @decription
 * @author minyan.he
 * @date 2024/6/25 11:59
 */
@Mapper
public interface CurrencyAccountMapper {
  CurrencyAccountPO selectByUserIdAndCurrencyType(
      @Param("userId") String userId, @Param("currencyType") Integer currencyType);

  int insertSelective(CurrencyAccountPO record);

  int updateByUserIdAndCurrencyType(
      @Param("userId") String userId,
      @Param("currencyType") Integer currencyType,
      @Param("addCurrency") BigDecimal addCurrency,
      @Param("deductCurrency") BigDecimal deductCurrency);
}
