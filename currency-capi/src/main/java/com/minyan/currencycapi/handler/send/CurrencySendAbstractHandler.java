package com.minyan.currencycapi.handler.send;

import com.minyan.vo.context.send.SendContext;
import org.springframework.stereotype.Service;

/**
 * @decription
 * @author minyan.he
 * @date 2024/6/30 18:48
 */
@Service
public abstract class CurrencySendAbstractHandler implements CurrencySendHandler {
  /** 回退处理器——默认不做回退操作 */
  @Override
  public void fallBack(SendContext sendContext) {}
}
