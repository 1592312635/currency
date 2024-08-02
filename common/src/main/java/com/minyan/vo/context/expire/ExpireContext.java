package com.minyan.vo.context.expire;

import com.minyan.po.CurrencyOrderPO;
import lombok.Data;

/**
 * @decription 代币过期中间变量
 * @author minyan.he
 * @date 2024/8/2 10:05
 */
@Data
public class ExpireContext {
  private CurrencyOrderPO expireOrderPO;
}
