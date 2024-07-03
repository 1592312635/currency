package com.minyan.dao;

import com.minyan.po.CurrencySerialPO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @decription
 * @author minyan.he
 * @date 2024/6/25 11:59
 */
@Mapper
public interface CurrencySerialMapper {
  int insertSelective(CurrencySerialPO record);

  List<CurrencySerialPO> querySerial(
      @Param("userId") String userId,
      @Param("currencyType") Integer currencyType,
      @Param("handleType") Integer handleType,
      @Param("pageNum") Integer pageNum,
      @Param("pageSize") Integer pageSize);
}
