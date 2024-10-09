package com.minyan.currencycrond.handler.expire;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.dao.CurrencyAccountMapper;
import com.minyan.po.CurrencyAccountPO;
import com.minyan.po.CurrencyOrderPO;
import com.minyan.vo.context.ExpireContext;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @decription 代币过期账户处理
 * @author minyan.he
 * @date 2024/8/2 10:45
 */
@Service
@Order(1)
public class CurrencyExpireAccountHandler extends CurrencyExpireAbstractHandler {
  private static final Logger logger = LoggerFactory.getLogger(CurrencyExpireAccountHandler.class);
  @Autowired private CurrencyAccountMapper currencyAccountMapper;

  @Override
  public boolean handle(ExpireContext expireContext) {
    CurrencyOrderPO expireOrderPO = expireContext.getExpireOrderPO();
    // 查询并验证当前订单账户信息
    CurrencyAccountPO currencyAccountPO =
        currencyAccountMapper.selectByUserIdAndCurrencyType(
            expireOrderPO.getUserId(), expireOrderPO.getCurrencyType());
    if (ObjectUtils.isEmpty(currencyAccountPO)) {
      logger.info(
          "[CurrencyExpireAccountHandler][handle]代币过期-账户过期时未查询到对应账户，订单信息：{}",
          JSONObject.toJSONString(expireOrderPO));
      return false;
    }
    BigDecimal expireAmount =
        expireOrderPO
                    .getAmount()
                    .subtract(expireOrderPO.getFailAmount())
                    .subtract(expireOrderPO.getExpireAmount())
                    .compareTo(currencyAccountPO.getCurrency())
                > 0
            ? currencyAccountPO.getCurrency()
            : expireOrderPO
                .getAmount()
                .subtract(expireOrderPO.getFailAmount())
                .subtract(expireOrderPO.getExpireAmount());
    expireContext.setExpireAmount(expireAmount);

    // 扣减账户
    currencyAccountMapper.updateByUserIdAndCurrencyType(
        expireOrderPO.getUserId(), expireOrderPO.getCurrencyType(), null, expireAmount);
    logger.info(
        "[CurrencyExpireAccountHandler][handle]代币过期-账户处理完成，订单号：{}，过期金额：{}",
        expireOrderPO.getOrderNo(),
        expireAmount);
    return true;
  }
}
