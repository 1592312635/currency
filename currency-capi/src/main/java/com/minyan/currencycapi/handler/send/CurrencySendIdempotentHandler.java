package com.minyan.currencycapi.handler.send;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.dao.CurrencyIdempotentMapper;
import com.minyan.param.AccountSendParam;
import com.minyan.po.CurrencyIdempotentPO;
import com.minyan.vo.send.SendContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @decription 幂等信息处理handler
 * @author minyan.he
 * @date 2024/6/30 18:50
 */
@Service
@Order(10)
public class CurrencySendIdempotentHandler extends CurrencySendAbstractHandler {
  private static final Logger logger = LoggerFactory.getLogger(CurrencySendIdempotentHandler.class);
  @Autowired private CurrencyIdempotentMapper currencyIdempotentMapper;

  @Override
  public boolean handle(SendContext sendContext) {
    AccountSendParam param = sendContext.getParam();
    int count = currencyIdempotentMapper.countByBusinessId(param.getBusinessId());
    logger.info(
        "[CurrencySendIdempotentHandler][handle]幂等校验结束，请求参数：{}，返回结果：{}",
        JSONObject.toJSONString(param),
        count);
    if (count > 0) {
      logger.info(
          "[CurrencySendIdempotentHandler][handle]代币发放幂等性校验不通过，已存在挡墙流水号信息，请求参数：{}，返回结果：{}",
          JSONObject.toJSONString(param),
          count);
      return false;
    }
    // 构建新的流水幂等信息数据
    CurrencyIdempotentPO currencyIdempotentPO = buildCurrencyIdempotentPO(param);
    int result = currencyIdempotentMapper.insertSelective(currencyIdempotentPO);
    return result > 0;
  }

  CurrencyIdempotentPO buildCurrencyIdempotentPO(AccountSendParam param) {
    CurrencyIdempotentPO po = new CurrencyIdempotentPO();
    po.setBusinessId(param.getBusinessId());
    po.setBehaviorCode(param.getBehaviorCode());
    return po;
  }
}
