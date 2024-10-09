package com.minyan.currencycrond.handler.expire;

import com.minyan.Enum.OrderStatusEnum;
import com.minyan.dao.CurrencyOrderMapper;
import com.minyan.po.CurrencyOrderPO;
import com.minyan.vo.context.ExpireContext;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @decription 代币过期订单处理
 * @author minyan.he
 * @date 2024/8/2 10:45
 */
@Service
@Order(2)
public class CurrencyExpireOrderHandler extends CurrencyExpireAbstractHandler {
  private static final Logger logger = LoggerFactory.getLogger(CurrencyExpireOrderHandler.class);
  @Autowired private CurrencyOrderMapper currencyOrderMapper;

  @Override
  public boolean handle(ExpireContext expireContext) {
    CurrencyOrderPO expireOrderPO = expireContext.getExpireOrderPO();
    BigDecimal expireAmount = expireContext.getExpireAmount();

    // 过期订单
    CurrencyOrderPO updateCurrencyOrderPO = new CurrencyOrderPO();
    updateCurrencyOrderPO.setId(expireOrderPO.getId());
    updateCurrencyOrderPO.setStatus(OrderStatusEnum.EXPIRE.getValue());
    updateCurrencyOrderPO.setExpireAmount(expireOrderPO.getExpireAmount().add(expireAmount));
    currencyOrderMapper.updateStatusAndAmountById(updateCurrencyOrderPO);
    logger.info(
        "[CurrencyExpireOrderHandler][handle]代币过期-订单过期完成，订单号：{}，过期金额：{}",
        expireOrderPO.getOrderNo(),
        expireAmount);
    return true;
  }
}
