package com.minyan.currencycapi.handler.confirm.success;

import com.minyan.vo.context.confirm.ConfirmContext;
import org.springframework.stereotype.Service;

/**
 * @decription 订单确认成功抽象类
 * @author minyan.he
 * @date 2024/9/1 11:44
 */
@Service
public abstract class OrderConfirmSuccessAbstractHandler implements OrderConfirmSuccessHandler {
  @Override
  public void fallBack(ConfirmContext confirmContext) {}
}
