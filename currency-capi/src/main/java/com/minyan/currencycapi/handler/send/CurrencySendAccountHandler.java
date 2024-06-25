package com.minyan.currencycapi.handler.send;

import com.minyan.dao.CurrencyAccountMapper;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * @decription 代币发放账户处理handler
 * @author minyan.he
 * @date 2024/6/25 13:45
 */
@Service
@Order(50)
public class CurrencySendAccountHandler {
    Logger logger = Logger.getLogger(CurrencySendAccountHandler.class.getName());

    @Autowired private CurrencyAccountMapper currencyAccountMapper;


}
