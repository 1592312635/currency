package com.minyan.currencycapi.handler.send;

/**
 * @decription 代币发放处理handler
 * @author minyan.he
 * @date 2024/6/25 13:45
 */
public interface CurrencySendHandler {
    boolean handle();

    void fallBack();
}
