package com.minyan.currencycapi.handler.send;

import com.alibaba.fastjson2.JSONObject;
import com.minyan.Enum.CycleEnum;
import com.minyan.Enum.EffectiveTypeEnum;
import com.minyan.Enum.HandleTypeEnum;
import com.minyan.dao.CurrencyOrderMapper;
import com.minyan.dao.CurrencyRuleMapper;
import com.minyan.param.AccountSendParam;
import com.minyan.po.CurrencyOrderPO;
import com.minyan.po.CurrencyRulePO;
import com.minyan.utils.TimeUtil;
import com.minyan.vo.context.send.SendContext;
import java.util.Date;
import java.util.Objects;
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
  @Autowired private CurrencyRuleMapper currencyRuleMapper;

  @Override
  public boolean handle(SendContext sendContext) {
    AccountSendParam param = sendContext.getParam();
    CurrencyRulePO currencyRulePO = sendContext.getCurrencyRulePO();

    // 计算失效时间
    Date expireDate = caculateExpireTime(currencyRulePO);
    CurrencyOrderPO currencyOrderPO = buildCurrencyOrderPO(param, expireDate);
    int result = currencyOrderMapper.insertSelective(currencyOrderPO);
    logger.info(
        "[CurrencySendOrderHandler][handle]代币发放时生成订单结束，请求参数：{}，返回结果：{}",
        JSONObject.toJSONString(currencyOrderPO),
        result);
    return true;
  }

  /**
   * 构建订单信息
   *
   * @param param
   * @param expireDate
   * @return
   */
  CurrencyOrderPO buildCurrencyOrderPO(AccountSendParam param, Date expireDate) {
    CurrencyOrderPO po = new CurrencyOrderPO();
    po.setUserId(param.getUserId());
    po.setAmount(param.getAddCurrency());
    po.setCurrencyType(param.getCurrencyType());
    po.setHandleType(HandleTypeEnum.ADD.getValue());
    po.setBehaviorCode(param.getBehaviorCode());
    po.setOrderNo(param.getBusinessId());
    po.setExpireTime(expireDate);
    return po;
  }

  /**
   * 计算到期时间
   *
   * @param currencyRulePO
   * @return
   */
  Date caculateExpireTime(CurrencyRulePO currencyRulePO) {
    Date expireTime = null, now = new Date();
    switch (Objects.requireNonNull(
        EffectiveTypeEnum.getEffectiveTypeEnumByValue(currencyRulePO.getEffectiveType()))) {
      case ABSOLUTE:
        expireTime = currencyRulePO.getEndTime();
        break;
      case RELATIVE:
        expireTime =
            TimeUtil.adjustDate(
                now,
                Objects.requireNonNull(
                    CycleEnum.getCycleEnumByValue(currencyRulePO.getExpireCycle())),
                currencyRulePO.getExpireSpan());
        break;
      case NATURE:
        expireTime =
            TimeUtil.adjustDate(
                currencyRulePO.getStartTime(),
                CycleEnum.getCycleEnumByValue(currencyRulePO.getEffectiveCycle()),
                currencyRulePO.getEffectiveSpan());
        break;
    }
    return expireTime;
  }
}
