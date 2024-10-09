package com.minyan.vo.context;

import com.minyan.po.CurrencyOrderPO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @decription 代币过期中间变量
 * @author minyan.he
 * @date 2024/8/2 10:05
 */
@Data
public class ExpireContext {
  private CurrencyOrderPO expireOrderPO;
  private BigDecimal expireAmount;
}
