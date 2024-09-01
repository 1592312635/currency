package com.minyan.currencycapi.service;

import com.minyan.param.OrderConfirmParam;

/**
 * @decription
 * @author minyan.he
 * @date 2024/9/1 10:31
 */
public interface OrderService {
    boolean confirmOrder(OrderConfirmParam param);
}
