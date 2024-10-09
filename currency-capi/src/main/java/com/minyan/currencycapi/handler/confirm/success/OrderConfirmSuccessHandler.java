package com.minyan.currencycapi.handler.confirm.success;

import com.minyan.vo.context.ConfirmContext;

/**
 * @decription 订单确认成功处理handler
 * @author minyan.he
 * @date 2024/9/1 11:41
 */
public interface OrderConfirmSuccessHandler {
  boolean handle(ConfirmContext confirmContext);

  void fallBack(ConfirmContext confirmContext);
}
