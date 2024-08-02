package com.minyan.currencycrond.service;

import com.minyan.po.CurrencyOrderPO;

/**
 * @decription
 * @author minyan.he
 * @date 2024/8/1 13:58
 */
public interface CurrencyExpireTaskService {
    void expireCurrencyOrder(CurrencyOrderPO currencyOrderPO);
}
