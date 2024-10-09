package com.minyan.currencycapi.handler.confirm.fail;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.Enum.HandleTypeEnum;
import com.minyan.dao.CurrencyAccountMapper;
import com.minyan.param.OrderConfirmParam;
import com.minyan.po.CurrencyOrderPO;
import com.minyan.vo.context.ConfirmContext;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @decription 订单确认失败账户处理handler
 * @author minyan.he
 * @date 2024/9/9 21:15
 */
@Service
@Order(20)
public class OrderConfirmFailAmountHandler extends OrderConfirmFailAbstractHandler {
  Logger logger = LoggerFactory.getLogger(OrderConfirmFailAmountHandler.class);

  @Autowired private CurrencyAccountMapper currencyAccountMapper;

  @Override
  public boolean handle(ConfirmContext confirmContext) {
    OrderConfirmParam param = confirmContext.getParam();
    CurrencyOrderPO currencyOrderPO = confirmContext.getCurrencyOrderPO();
    BigDecimal failAmount = confirmContext.getFailAmount();
    currencyAccountMapper.updateByUserIdAndCurrencyType(
        param.getUserId(),
        param.getCurrencyType(),
        HandleTypeEnum.ADD.getValue().equals(currencyOrderPO.getHandleType()) ? null : failAmount,
        HandleTypeEnum.ADD.getValue().equals(currencyOrderPO.getHandleType()) ? failAmount : null);
    logger.info(
        "[OrderConfirmFailAmountHandler][handle]订单确认失败账户回退成功，请求参数：{}，回退金额：{}",
        JSONObject.toJSONString(param),
        failAmount);
    return true;
  }
}
