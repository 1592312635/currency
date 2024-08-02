package com.minyan.dao;

import com.minyan.po.CurrencyRulePO;
import java.util.Date;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @decription 代币规则信息mapper
 * @author minyan.he
 * @date 2024/6/25 18:27
 */
@Mapper
public interface CurrencyRuleMapper {

  CurrencyRulePO selectByCurrencyType(@Param("currencyType") Integer currencyType);

  int insertSelective(CurrencyRulePO record);

  int updateStartTimeById(@Param("id") Long id, @Param("startTime") Date startTime);
}
