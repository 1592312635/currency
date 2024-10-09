package com.minyan.vo.context;

import com.minyan.param.OrderConfirmParam;
import com.minyan.po.CurrencyOrderPO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @decription
 * @author minyan.he
 * @date 2024/9/1 11:46
 */
@Data
public class ConfirmContext {
  private OrderConfirmParam param;
  // 订单信息
  private CurrencyOrderPO currencyOrderPO;
  // 失败回退金额
  private BigDecimal failAmount;
}
