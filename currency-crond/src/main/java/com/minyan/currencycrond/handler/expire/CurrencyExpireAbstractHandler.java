package com.minyan.currencycrond.handler.expire;

import com.minyan.vo.context.expire.ExpireContext;
import org.springframework.stereotype.Service;

/**
 * @decription 代币过期handler
 * @author minyan.he
 * @date 2024/8/2 10:08
 */
@Service
public abstract class CurrencyExpireAbstractHandler implements CurrencyExpireHandler {

  @Override
  public void fallBack(ExpireContext expireContext) {}
}
