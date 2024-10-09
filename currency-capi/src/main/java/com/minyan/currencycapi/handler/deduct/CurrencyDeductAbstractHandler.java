package com.minyan.currencycapi.handler.deduct;

import com.minyan.vo.context.DeductContext;
import org.springframework.stereotype.Service;

/**
 * @decription 代币扣减抽象类
 * @author minyan.he
 * @date 2024/7/3 10:35
 */
@Service
public abstract class CurrencyDeductAbstractHandler implements CurrencyDeductHandler {
  public void fallBack(DeductContext deductContext) {}
}
