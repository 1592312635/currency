package com.minyan.currencycapi.handler.confirm.fail;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.Enum.OrderStatusEnum;
import com.minyan.dao.CurrencyOrderMapper;
import com.minyan.param.OrderConfirmParam;
import com.minyan.po.CurrencyOrderPO;
import com.minyan.vo.context.confirm.ConfirmContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @decription 代币订单确认失败处理handler
 * @author minyan.he
 * @date 2024/9/1 12:39
 */
@Service
@Order(10)
public class OrderConfirmFailOrderHandler extends OrderConfirmFailAbstractHandler {
  public static final Logger logger = LoggerFactory.getLogger(OrderConfirmFailOrderHandler.class);

  @Autowired private CurrencyOrderMapper currencyOrderMapper;

  @Override
  public boolean handle(ConfirmContext confirmContext) {
    OrderConfirmParam param = confirmContext.getParam();
    CurrencyOrderPO currencyOrderPO =
        currencyOrderMapper.queryOrderExist(
            param.getUserId(), param.getUserId(), param.getCurrencyType());
    if (ObjectUtils.isEmpty(currencyOrderPO)) {
      logger.info(
          "[OrderConfirmFailOrderHandler][handle]代币订单确认失败，未查询到订单信息，请求参数：{}",
          JSONObject.toJSONString(param));
      return false;
    }
    if (OrderStatusEnum.FAIL.getValue().equals(currencyOrderPO.getStatus())) {
      logger.info(
          "[OrderConfirmFailOrderHandler][handle]订单已为失败状态，无需回退，订单信息：{}",
          JSONObject.toJSONString(currencyOrderPO));
      return false;
    }
    confirmContext.setCurrencyOrderPO(currencyOrderPO);
    // 设置本次需要回退金额
    confirmContext.setFailAmount(
        currencyOrderPO
            .getAmount()
            .subtract(currencyOrderPO.getFailAmount())
            .subtract(currencyOrderPO.getExpireAmount()));
    currencyOrderPO.setId(currencyOrderPO.getId());
    currencyOrderPO.setStatus(OrderStatusEnum.FAIL.getValue());
    currencyOrderPO.setFailAmount(
        currencyOrderPO.getAmount().subtract(currencyOrderPO.getExpireAmount()));
    currencyOrderMapper.updateStatusAndAmountById(currencyOrderPO);
    return true;
  }
}
