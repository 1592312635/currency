package com.minyan.currencycapi.handler.confirm.success;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.Enum.OrderStatusEnum;
import com.minyan.dao.CurrencyOrderMapper;
import com.minyan.param.OrderConfirmParam;
import com.minyan.po.CurrencyOrderPO;
import com.minyan.vo.context.confirm.ConfirmContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @decription 订单确认订单处理handler
 * @author minyan.he
 * @date 2024/9/1 12:18
 */
@Service
public class OrderConfirmSuccessOrderHandler extends OrderConfirmSuccessAbstractHandler {
  public static final Logger logger =
      LoggerFactory.getLogger(OrderConfirmSuccessOrderHandler.class);

  @Autowired private CurrencyOrderMapper currencyOrderMapper;

  @Override
  public boolean handle(ConfirmContext confirmContext) {
    OrderConfirmParam param = confirmContext.getParam();
    CurrencyOrderPO currencyOrderPO =
        currencyOrderMapper.queryOrderExist(
            param.getUserId(), param.getOrderNo(), param.getCurrencyType());
    if (ObjectUtils.isEmpty(currencyOrderPO)) {
      logger.info(
          "[OrderConfirmSuccessOrderHandler][handle]代币订单确认时订单不存在，请求参数：{}",
          JSONObject.toJSONString(param));
      return false;
    }
    if (OrderStatusEnum.SUCCESS.getValue().equals(currencyOrderPO.getStatus())) {
      logger.info(
          "[OrderConfirmSuccessOrderHandler][handle]订单确认成功时订单已成功无需操作，订单信息：{}",
          JSONObject.toJSONString(currencyOrderPO));
    }
    currencyOrderPO.setStatus(OrderStatusEnum.SUCCESS.getValue());
    currencyOrderMapper.updateStatusAndAmountById(currencyOrderPO);
    return true;
  }
}
