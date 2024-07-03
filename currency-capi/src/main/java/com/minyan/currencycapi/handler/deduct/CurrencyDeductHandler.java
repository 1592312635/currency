package com.minyan.currencycapi.handler.deduct;

import com.minyan.vo.deduct.DeductContext;

/**
 * @decription 代币扣减处理handler
 * @author minyan.he
 * @date 2024/7/3 10:30
 */
public interface CurrencyDeductHandler {
  boolean handle(DeductContext deductContext);

  void fallBack(DeductContext deductContext);
}
