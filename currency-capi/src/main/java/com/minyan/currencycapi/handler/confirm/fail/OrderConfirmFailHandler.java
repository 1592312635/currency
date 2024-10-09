package com.minyan.currencycapi.handler.confirm.fail;

import com.minyan.vo.context.ConfirmContext;

/**
 * @decription 订单确认失败处理handler
 * @author minyan.he
 * @date 2024/9/1 11:42
 */
public interface OrderConfirmFailHandler {
  boolean handle(ConfirmContext confirmContext);

  void fallBack(ConfirmContext confirmContext);
}
