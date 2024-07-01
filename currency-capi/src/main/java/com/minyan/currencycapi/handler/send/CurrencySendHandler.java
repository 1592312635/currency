package com.minyan.currencycapi.handler.send;

import com.minyan.exception.CurrencyException;
import com.minyan.vo.send.SendContext;
import lombok.SneakyThrows;

/**
 * @decription 代币发放处理handler
 * @author minyan.he
 * @date 2024/6/25 13:45
 */
public interface CurrencySendHandler {
  boolean handle(SendContext sendContext) throws CurrencyException;

  void fallBack(SendContext sendContext);
}
