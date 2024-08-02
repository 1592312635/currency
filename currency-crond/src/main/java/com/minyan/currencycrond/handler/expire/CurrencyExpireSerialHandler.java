package com.minyan.currencycrond.handler.expire;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.Enum.HandleTypeEnum;
import com.minyan.dao.CurrencySerialMapper;
import com.minyan.po.CurrencyOrderPO;
import com.minyan.po.CurrencySerialPO;
import com.minyan.vo.context.expire.ExpireContext;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @decription 代币过期流水处理
 * @author minyan.he
 * @date 2024/8/2 10:46
 */
@Service
@Order(3)
public class CurrencyExpireSerialHandler extends CurrencyExpireAbstractHandler {
  private static final Logger logger = LoggerFactory.getLogger(CurrencyExpireSerialHandler.class);
  @Autowired private CurrencySerialMapper currencySerialMapper;

  @Override
  public boolean handle(ExpireContext expireContext) {
    BigDecimal expireAmount = expireContext.getExpireAmount();
    CurrencyOrderPO expireOrderPO = expireContext.getExpireOrderPO();
    CurrencySerialPO currencySerialPO = buildExpireSerialPO(expireAmount, expireOrderPO);
    currencySerialMapper.insertSelective(currencySerialPO);
    logger.info(
        "[CurrencyExpireSerialHandler][handle]代币过期-流水处理完成，订单号：{}，流水信息：{}",
        expireOrderPO.getOrderNo(),
        JSONObject.toJSONString(currencySerialPO));
    return false;
  }

  /**
   * 构建过期流水
   *
   * @param expireAmount
   * @param expireOrderPO
   * @return
   */
  CurrencySerialPO buildExpireSerialPO(BigDecimal expireAmount, CurrencyOrderPO expireOrderPO) {
    CurrencySerialPO currencySerialPO = new CurrencySerialPO();
    currencySerialPO.setAmount(expireAmount);
    currencySerialPO.setBehaviorCode("expire");
    currencySerialPO.setBehaviorDesc("过期");
    currencySerialPO.setBusinessId(expireOrderPO.getOrderNo());
    currencySerialPO.setUserId(expireOrderPO.getUserId());
    currencySerialPO.setCurrencyType(expireOrderPO.getCurrencyType());
    currencySerialPO.setHandleType(HandleTypeEnum.REDUCE.getValue());
    return currencySerialPO;
  }
}
