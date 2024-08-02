package com.minyan.currencycapi.handler.send;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.Enum.HandleTypeEnum;
import com.minyan.dao.CurrencyOrderMapper;
import com.minyan.param.AccountSendParam;
import com.minyan.po.CurrencyOrderPO;
import com.minyan.vo.context.send.SendContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @decription 代币发放订单处理器
 * @author minyan.he
 * @date 2024/7/13 16:16
 */
@Order(30)
@Service
public class CurrencySendOrderHandler extends CurrencySendAbstractHandler {
  private static final Logger logger = LoggerFactory.getLogger(CurrencySendOrderHandler.class);

  @Autowired private CurrencyOrderMapper currencyOrderMapper;

  @Override
  public boolean handle(SendContext sendContext) {
    AccountSendParam param = sendContext.getParam();
    CurrencyOrderPO currencyOrderPO = buildCurrencyOrderPO(param);
    int result = currencyOrderMapper.insertSelective(currencyOrderPO);
    logger.info(
        "[CurrencySendOrderHandler][handle]代币发放时生成订单结束，请求参数：{}，返回结果：{}",
        JSONObject.toJSONString(currencyOrderPO),
        result);
    return true;
  }

  CurrencyOrderPO buildCurrencyOrderPO(AccountSendParam param) {
    CurrencyOrderPO po = new CurrencyOrderPO();
    po.setUserId(param.getUserId());
    po.setAmount(param.getAddCurrency());
    po.setCurrencyType(param.getCurrencyType());
    po.setHandleType(HandleTypeEnum.ADD.getValue());
    po.setBehaviorCode(param.getBehaviorCode());
    po.setOrderNo(param.getBusinessId());
    return po;
  }
}
