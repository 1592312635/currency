package com.minyan.currencycapi.handler.send;

import com.minyan.Enum.HandleTypeEnum;
import com.minyan.dao.CurrencySerialMapper;
import com.minyan.param.AccountSendParam;
import com.minyan.po.CurrencySerialPO;
import com.minyan.vo.send.SendContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @decription
 * @author minyan.he
 * @date 2024/7/2 11:10
 */
@Service
@Order(60)
public class CurrencySendSerialHandler extends CurrencySendAbstractHandler {
  private static final Logger logger = LoggerFactory.getLogger(CurrencySendSerialHandler.class);

  @Autowired private CurrencySerialMapper currencySerialMapper;

  @Override
  public boolean handle(SendContext sendContext) {
    AccountSendParam param = sendContext.getParam();
    CurrencySerialPO currencySerialPO = buildCurrencySerialPO(param);
    int result = currencySerialMapper.insertSelective(currencySerialPO);
    return result > 0;
  }

  /**
   * 构建代币流水
   *
   * @param param
   * @return
   */
  CurrencySerialPO buildCurrencySerialPO(AccountSendParam param) {
    CurrencySerialPO currencySerialPO = new CurrencySerialPO();
    currencySerialPO.setBusinessId(param.getBusinessId());
    currencySerialPO.setUserId(param.getUserId());
    currencySerialPO.setBehaviorCode(param.getBehaviorCode());
    currencySerialPO.setHandleType(HandleTypeEnum.ADD.getValue());
    currencySerialPO.setCurrencyType(param.getCurrencyType());
    currencySerialPO.setAmount(param.getAddCurrency());
    return currencySerialPO;
  }
}
