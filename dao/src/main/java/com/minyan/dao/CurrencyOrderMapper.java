package com.minyan.dao;

import com.minyan.po.CurrencyOrderPO;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @decription 订单表mapper
 * @author minyan.he
 * @date 2024/6/25 11:59
 */
@Mapper
public interface CurrencyOrderMapper {
  int insertSelective(CurrencyOrderPO record);

  List<CurrencyOrderPO> queryOrdersByStatusList(
      @Param("userId") String userId,
      @Param("currencyType") Integer currencyType,
      @Param("handleType") Integer handleType,
      @Param("statusList") List<Integer> statusList,
      @Param("pageNum") Integer pageNum,
      @Param("pageSize") Integer pageSize);

  int updateStatusAndAmountById(CurrencyOrderPO currencyOrderPO);

  List<CurrencyOrderPO> queryExpireOrders(
      @Param("handleType") Integer handleType,
      @Param("statusList") List<Integer> statusList,
      @Param("pageSize") Integer pageSize);

  int updateScheduleTimeById(@Param("id") Long id, @Param("scheduleTime") Date scheduleTime);

  CurrencyOrderPO queryOrderExist(
      @Param("userId") String userId,
      @Param("orderNo") String orderNo,
      @Param("currencyType") Integer currencyType);
}
