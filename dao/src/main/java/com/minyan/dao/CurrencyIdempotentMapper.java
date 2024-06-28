package com.minyan.dao;

import com.minyan.po.CurrencyIdempotentPO;
import org.apache.ibatis.annotations.Param;

/**
 * @decription 幂等信息mapper
 * @author minyan.he
 * @date 2024/6/25 18:27
 */
public interface CurrencyIdempotentMapper {
  int countByBusinessId(@Param("businessId") String businessId);

  int insertSelective(CurrencyIdempotentPO record);
}
