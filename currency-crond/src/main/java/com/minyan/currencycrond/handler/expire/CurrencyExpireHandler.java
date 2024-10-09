package com.minyan.currencycrond.handler.expire;

import com.minyan.vo.context.ExpireContext;

/**
 * @decription 代币过期处理
 * @author minyan.he
 * @date 2024/8/2 10:04
 */
public interface CurrencyExpireHandler {
  boolean handle(ExpireContext expireContext);

  void fallBack(ExpireContext expireContext);
}
