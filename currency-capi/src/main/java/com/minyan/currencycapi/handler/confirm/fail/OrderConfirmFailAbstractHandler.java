package com.minyan.currencycapi.handler.confirm.fail;

import com.minyan.vo.context.ConfirmContext;
import org.springframework.stereotype.Service;

/**
 * @decription 订单确认失败抽象类
 * @author minyan.he
 * @date 2024/9/1 11:44
 */
@Service
public abstract class OrderConfirmFailAbstractHandler implements OrderConfirmFailHandler {
  @Override
  public void fallBack(ConfirmContext confirmContext) {}
}
